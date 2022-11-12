package ru.practicum.eventRequest.service;

import ru.practicum.eventRequest.dto.EventRequestDto;

import java.util.List;

public interface EventRequestService {

    /**
     * @param userId
     * @return
     */
    List<EventRequestDto> getRequests(Long userId);

    /**
     * @param userId
     * @param eventId
     * @return
     */
    EventRequestDto addRequest(Long userId, Long eventId);

    /**
     * @param userId
     * @param requestId
     * @return
     */
    EventRequestDto cancelRequest(Long userId, Long requestId);
}
