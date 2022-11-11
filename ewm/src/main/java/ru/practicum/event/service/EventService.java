package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

public interface EventService {

    //admin controller start

    /**
     *
     * @return
     */
    List<EventFullDto> retrieveEvents();

    /**
     *
     * @param eventFullDto
     * @param eventId
     * @return
     */
    EventFullDto updateEvent(EventFullDto eventFullDto, Long eventId);

    /**
     *
     * @param eventId
     * @return
     */
    EventFullDto publishEvent(Long eventId);

    /**
     *
     * @param eventId
     * @return
     */
    EventFullDto rejectEvent(Long eventId);
    //admin controller end

    //public controller start

    /**
     *
     * @return
     */
    List<EventShortDto> retrievePublicEvents();

    /**
     *
     * @param eventId
     * @return
     */
    List<EventFullDto> retrievePublicEventById(Long eventId);
    //public controller end

    //private controller start

    /**
     *
     * @param userId
     * @param from
     * @param size
     * @return
     */
    List<EventFullDto> retrieveEventsByCreator(Long userId, Integer from, Integer size);

    /**
     *
     * @param userId
     * @param eventFullDto
     * @return
     */
    EventFullDto updateEventByCreator(Long userId, EventFullDto eventFullDto);

    /**
     *
     * @param userId
     * @param eventFullDto
     * @return
     */
    EventFullDto createEvent(Long userId, EventFullDto eventFullDto);

    /**
     *
     * @param userId
     * @param eventId
     * @return
     */
    List<EventFullDto> retrieveEventsByIdForCreator(Long userId, Long eventId);

    /**
     *
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto cancelEventByCreator(Long userId, Long eventId);

    /**
     *
     * @param userId
     * @param eventId
     * @return
     */
    List<EventFullDto> retrieveRequestEventByUser(Long userId, Long eventId);

    /**
     *
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto confirmRequestForEvent(Long userId, Long eventId);

    /**
     *
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto rejectRequestForEvent(Long userId, Long eventId);

    //private controller end
}
