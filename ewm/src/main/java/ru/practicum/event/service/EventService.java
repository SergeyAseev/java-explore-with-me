package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.Sort;
import ru.practicum.eventRequest.dto.EventRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    //admin controller start

    /**
     * @return
     */
    List<EventFullDto> retrieveEvents(List<Long> userIds, List<EventState> stateIds, List<Integer> catIds,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * @param eventFullDto
     * @param eventId
     * @return
     */
    EventFullDto updateEvent(NewEventDto eventFullDto, Long eventId);

    /**
     * @param eventId
     * @return
     */
    EventFullDto publishEvent(Long eventId);

    /**
     * @param eventId
     * @return
     */
    EventFullDto rejectEvent(Long eventId);
    //admin controller end

    //public controller start

    /**
     * @return
     */
    List<EventShortDto> retrievePublicEvents(String text, List<Integer> catIds, Boolean paid, String rangeStart,
                                             String rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size);


    /**
     * @param eventId
     * @return
     */
    EventFullDto retrievePublicEventById(Long eventId);
    //public controller end

    //private controller start

    /**
     * @param userId
     * @param from
     * @param size
     * @return
     */
    List<EventFullDto> retrieveEventsByCreator(Long userId, Integer from, Integer size);

    /**
     * @param userId
     * @param eventFullDto
     * @return
     */
    EventFullDto updateEventByCreator(Long userId, NewEventDto eventFullDto);

    /**
     * @param userId
     * @param eventFullDto
     * @return
     */
    EventFullDto createEvent(Long userId, NewEventDto eventFullDto);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto retrieveEventByIdForCreator(Long userId, Long eventId);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto cancelEventByCreator(Long userId, Long eventId);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    List<EventRequestDto> retrieveRequestEventByUser(Long userId, Long eventId);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    EventRequestDto confirmRequestForEvent(Long userId, Long eventId, Long reqId);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    EventRequestDto rejectRequestForEvent(Long userId, Long eventId, Long reqId);

    //private controller end
}
