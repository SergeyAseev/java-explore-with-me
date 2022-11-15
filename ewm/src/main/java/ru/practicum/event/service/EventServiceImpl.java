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
import ru.practicum.eventRequest.dto.EventRequestDto;
import ru.practicum.eventRequest.dto.EventRequestMapper;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Override
    public List<EventFullDto> retrieveEvents(List<Long> userIds, List<EventState> stateIds, List<Integer> catIds,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {


        return null;
/*        return eventRepository.findAllByParams(userIds, stateIds, catIds, null, rangeStart, rangeEnd,
                null,  from, size).stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());*/
    }

    @Override
    public EventFullDto updateEvent(NewEventDto newEventDto, Long eventId) {

        getEventById(eventId);

/*        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            throw new ValidationException("Not enough time before event");
        }*/
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
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {

        Event event = getEventById(eventId);

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("You are ADMIN, but you don't have power in this case");
        }

        event.setState(EventState.CANCELED);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> retrievePublicEvents() {
        return null;
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
    public EventFullDto updateEventByCreator(Long userId, NewEventDto eventFullDto) {
        return null;
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

        return event.getRequests()
                .stream()
                .map(EventRequestMapper::toEventRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestDto confirmRequestForEvent(Long userId, Long eventId, Long reqId) {


        return null;
    }

    @Override
    public EventRequestDto rejectRequestForEvent(Long userId, Long eventId, Long reqId) {
        return null;
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with ID = %s not found", eventId)));
    }
}
