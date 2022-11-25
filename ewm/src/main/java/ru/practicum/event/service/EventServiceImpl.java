package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final EventRequestRepository eventRequestRepository;

    @Override
    public List<EventFullDto> retrieveEvents(List<Long> userIds, List<EventState> stateIds, List<Integer> catIds,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size);
        if (rangeStart == null) rangeStart = LocalDateTime.now();
        return eventRepository.searchEvents(userIds, stateIds, catIds,
                        rangeStart, rangeEnd, pageable)
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());

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

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    /**
     * @param newEventDto
     * @param event
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

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {

        Event event = getEventById(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("You are ADMIN, but you don't have power in this case");
        }
        event.setState(EventState.CANCELED);

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> retrievePublicEvents(String text, List<Integer> catIds, Boolean paid, LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size) {

        if (text.isBlank()) {
            return new ArrayList<>();
        }
        if (Objects.isNull(rangeStart)) {
            rangeStart = LocalDateTime.now();
        }

        List<EventShortDto> events = eventRepository.findEvents(text, catIds, paid, rangeStart, rangeEnd)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());


        if (onlyAvailable) {
            events = events.stream()
                    //.filter(e -> e.getConfirmedRequests() < e.getParticipantLimit() || e.getParticipantLimit() == 0)
                    .filter(e -> isRequestLimitReached(getEventById(e.getId())))
                    .collect(Collectors.toList());
        }
        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    events = events.stream()
                            .sorted(Comparator.comparing(EventShortDto::getEventDate))
                            .collect(Collectors.toList());
                    break;
                case VIEWS:
                    events = events.stream()
                            .sorted(Comparator.comparingLong(EventShortDto::getViews))
                            .collect(Collectors.toList());
                    break;
            }
        }

        return events.stream()
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto retrievePublicEventById(Long eventId) {

        getEventById(eventId);
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> retrieveEventsByCreator(Long userId, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(
                from / size,
                size);
        return eventRepository.findAllByInitiatorId(userId, pageable)
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByCreator(Long userId, NewEventDto newEventDto) {

        Event event = eventRepository.findById(newEventDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Not found event with id = " + newEventDto.getEventId()));

        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ValidationException(String.format("wrong time %s", newEventDto.getEventDate().toString()));
        }
/*        //Event event = EventMapper.toEvent(userId, newEventDto);
        Event event = eventRepository.findById(newEventDto.getEventId())
                .orElseThrow(() -> new NotFoundException("There is no Event =(( "));*/
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
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto retrieveEventByIdForCreator(Long userId, Long eventId) {

        return EventMapper.toEventFullDto(eventRepository.findByIdAndInitiatorId(eventId, userId));
    }

    @Override
    public EventFullDto cancelEventByCreator(Long userId, Long eventId) {

        getEventById(eventId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        event.setState(EventState.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
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
                .orElseThrow(() -> new NotFoundException(String.format("EventRequest with ID = %s not found", reqId)));

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
            event.getRequests().removeIf(e -> !e.getStatus().equals(RequestState.CONFIRMED));
        }
        eventRepository.save(event);

        return EventRequestMapper.toEventRequestDto(currentEventRequest);
    }

    @Override
    public EventRequestDto rejectRequestForEvent(Long userId, Long eventId, Long reqId) {

        Event event = getEventById(eventId);
        EventRequest currentEventRequest = eventRequestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(String.format("EventRequest with ID = %s not found", reqId)));
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
                .orElseThrow(() -> new NotFoundException(String.format("Event with ID = %s not found", eventId)));
    }

    /**
     * @param event
     * @return
     */
    private boolean isRequestLimitReached(Event event) {

        long limitConfirmed = event.getRequests().stream().filter(e -> e.getStatus().equals(RequestState.CONFIRMED)).count();
        return limitConfirmed >= event.getParticipantLimit();
    }
}
