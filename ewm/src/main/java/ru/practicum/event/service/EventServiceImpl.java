package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.client.service.StatClientService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventMapper;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.Sort;
import ru.practicum.eventRequest.dto.EventRequestDto;
import ru.practicum.eventRequest.dto.EventRequestMapper;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.eventRequest.model.RequestState;
import ru.practicum.eventRequest.service.EventRequestRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final EventRequestRepository eventRequestRepository;

    @Autowired
    private final StatClientService statClientService;

    @Override
    public List<EventFullDto> retrieveEvents(List<Long> userIds, List<EventState> stateIds, List<Integer> catIds,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size);
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        List<Event> events = eventRepository.searchEvents(userIds, stateIds, catIds, rangeStart, rangeEnd, pageable);
        return toListEventFullDto(events, false);
    }

    @Override
    public EventFullDto updateEvent(NewEventDto newEventDto, Long eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with ID %s not found", eventId)));
        checkChanges(newEventDto, event);

        if (newEventDto.getRequestModeration() != null) {
            event.setRequestModeration(newEventDto.getRequestModeration());
        }

        if (newEventDto.getLocation() != null) {
            Location location = Location.builder()
                    .lon(newEventDto.getLocation().getLon())
                    .lat(newEventDto.getLocation().getLat())
                    .build();
            event.setLocation(location);
        }

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {

        Event event = getEventById(eventId);

        if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
            throw new ValidationException("Not enough time before event");
        }

        if (!event.getState().equals(EventState.PENDING)) {
            throw new ValidationException("Event has to be in PENDING status");
        }

        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {

        Event event = getEventById(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("You are ADMIN, but you don't have power in this case");
        }
        event.setState(EventState.CANCELED);

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> retrievePublicEvents(String text, List<Integer> catIds, Boolean paid, LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size) {

        if (Objects.isNull(rangeStart)) {
            rangeStart = LocalDateTime.now();
        }

        List<Event> events = new ArrayList<>(eventRepository
                .findEvents(text, catIds, paid, rangeStart, rangeEnd, onlyAvailable));

        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    events = events.stream()
                            .sorted(Comparator.comparing(Event::getEventDate))
                            .collect(Collectors.toList());
                    break;
                case VIEWS:
                    List<EventShortDto> eventShortDto = toListEventShortDto(events, false);
                    return eventShortDto.stream()
                            .sorted(Comparator.comparingLong(EventShortDto::getViews).reversed())
                            .collect(Collectors.toList());
            }
        }

        return events.stream()
                .map(EventMapper::toEventShortDto)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto retrievePublicEventById(Long eventId) {

        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(String.format("Event with ID = %s wasn't found", eventId)));

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> retrieveEventsByCreator(Long userId, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(
                from / size,
                size);
        return toListEventShortDto(eventRepository.findAllByInitiatorId(userId, pageable), false);
    }

    @Override
    public EventFullDto updateEventByCreator(Long userId, NewEventDto newEventDto) {

        Event event = eventRepository.findById(newEventDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + newEventDto.getEventId()));

        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ValidationException(String.format("wrong time %s", newEventDto.getEventDate().toString()));
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator or admin can edit event");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("We can't edit a published event");
        }
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }

        return updateEvent(newEventDto, event.getId());
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {

        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ValidationException(String.format("wrong time %s", newEventDto.getEventDate().toString()));
        }
        Event event = EventMapper.toEvent(userId, newEventDto);
        eventRepository.save(event);

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto retrieveEventByIdForCreator(Long userId, Long eventId) {
        return makeFullDto(eventRepository.findByIdAndInitiatorId(eventId, userId));
    }

    @Override
    public EventFullDto cancelEventByCreator(Long userId, Long eventId) {

        getEventById(eventId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        event.setState(EventState.CANCELED);

        return makeFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventRequestDto> retrieveRequestEventByUser(Long userId, Long eventId) {

        Event event = getEventById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can get this information");
        }

        return eventRequestRepository.findAllByEventId(eventId)
                .stream()
                .map(EventRequestMapper::toEventRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestDto confirmRequestForEvent(Long userId, Long eventId, Long reqId) {

        Event event = getEventById(eventId);
        EventRequest currentEventRequest = eventRequestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(String.format("EventRequest with ID = %s wasn't found", reqId)));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can confirm a request");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ValidationException("No need to confirm");
        }
        if (isRequestLimitReached(event)) {
            throw new ValidationException("No more requests available");
        }
        currentEventRequest.setStatus(RequestState.CONFIRMED);
        if (isRequestLimitReached(event)) {
            event.getRequests().stream()
                    .filter(e -> !e.getStatus().equals(RequestState.CONFIRMED))
                    .forEach(eventRequest -> eventRequest.setStatus(RequestState.CANCELED));
        }
        eventRepository.save(event);

        return EventRequestMapper.toEventRequestDto(currentEventRequest);
    }

    @Override
    public EventRequestDto rejectRequestForEvent(Long userId, Long eventId, Long reqId) {

        Event event = getEventById(eventId);
        EventRequest currentEventRequest = eventRequestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(String.format("EventRequest with ID = %s wasn't found", reqId)));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can reject a request");
        }

        event.getRequests().remove(currentEventRequest);
        currentEventRequest.setStatus(RequestState.REJECTED);
        eventRepository.save(event);

        return EventRequestMapper.toEventRequestDto(currentEventRequest);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with ID = %s wasn't found", eventId)));
    }

    /**
     * проверяем, достигнут ли предел участников
     *
     * @param event экземпляр события
     * @return true|false
     */
    private boolean isRequestLimitReached(Event event) {

        long limitConfirmed = event.getRequests().stream().filter(e -> e.getStatus().equals(RequestState.CONFIRMED)).count();
        return limitConfirmed >= event.getParticipantLimit();
    }

    /**
     * Проверяем событие
     *
     * @param newEventDto экземпляр нового события
     * @param event       редактируемое событие
     */
    private void checkChanges(NewEventDto newEventDto, Event event) {

        if (newEventDto.getAnnotation() != null) {
            event.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getCategory() != null) {
            event.setCategory(Category.builder().id(newEventDto.getCategory()).build());
        }
        if (newEventDto.getDescription() != null) {
            event.setDescription(newEventDto.getDescription());
        }
        if (newEventDto.getEventDate() != null) {
            event.setEventDate(newEventDto.getEventDate());
        }
        if (newEventDto.getPaid() != null) {
            event.setPaid(newEventDto.getPaid());
        }
        if (newEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }
        if (newEventDto.getTitle() != null) {
            event.setTitle(newEventDto.getTitle());
        }
    }

    //Отдельно, чтобы не дублировать код
    private EventFullDto makeFullDto(Event event) {
        return EventMapper.toEventFullDto(event, eventRequestRepository
                        .countAllByStatusAndEventId(RequestState.CONFIRMED, event.getId()),
                statClientService.getViewsForEvent(event, false));
    }

    public List<EventShortDto> toListEventShortDto(List<Event> events, Boolean uniqueRequests) {

        List<Long> evs = events.stream().map(Event::getId).collect(Collectors.toList());
        Map<Long, Long> requests = new HashMap<>();
        for (Long[] temp : eventRepository
                .countAllByStatusAndEventIdIn(RequestState.CONFIRMED.name(), evs)) {
            requests.put(temp[0], temp[1]);
        }
        Map<Long, Long> views = statClientService.getViewsForEvents(events, uniqueRequests);

        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event,
                        views.get(event.getId()),
                        requests.get(Objects.isNull(event.getId()) ? 0 : event.getId())))
                .collect(Collectors.toList());
    }

    public List<EventFullDto> toListEventFullDto(List<Event> events, Boolean uniqueRequests) {

        List<Long> evs = events.stream().map(Event::getId).collect(Collectors.toList());
        Map<Long, Long> requests = new HashMap<>();
        for (Long[] temp : eventRepository
                .countAllByStatusAndEventIdIn(RequestState.CONFIRMED.name(), evs)) {
            requests.put(temp[0], temp[1]);
        }
        Map<Long, Long> views = statClientService.getViewsForEvents(events, uniqueRequests);

        return events.stream()
                .map(event -> EventMapper.toEventFullDto(event,
                        requests.get(Objects.isNull(event.getId()) ? 0 : event.getId()),
                        views.get(event.getId())))
                .collect(Collectors.toList());
    }
}
