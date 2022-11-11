package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

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
        return null;
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        return null;
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
}
