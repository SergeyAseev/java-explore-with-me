package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventMapper;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Override
    public List<EventFullDto> retrieveEvents() {
        return null;
    }

    @Override
    public EventFullDto updateEvent(EventFullDto eventFullDto, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {

        Event event = getEventById(eventId);
        event.setEventState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return null;
        //return EventMapper.eventRepository.save(event);
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {

        Event event = getEventById(eventId);
        event.setEventState(EventState.CANCEL);
        return null;
        //return EventMapper.eventRepository.save(event);
    }

    @Override
    public List<EventShortDto> retrievePublicEvents() {
        return null;
    }

    @Override
    public List<EventFullDto> retrievePublicEventById(Long eventId) {
        return null;
    }

    @Override
    public List<EventFullDto> retrieveEventsByCreator(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateEventByCreator(Long userId, EventFullDto eventFullDto) {
        return null;
    }

    @Override
    public EventFullDto createEvent(Long userId, EventFullDto eventFullDto) {
        return null;
    }

    @Override
    public List<EventFullDto> retrieveEventsByIdForCreator(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEventByCreator(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<EventFullDto> retrieveRequestEventByUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto confirmRequestForEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectRequestForEvent(Long userId, Long eventId) {
        return null;
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(()-> new NotFoundException(String.format("Event with ID = %s not found", eventId)));
    }
}
