package ru.practicum.eventRequest.service;

import ru.practicum.eventRequest.dto.EventRequestDto;

import java.util.List;

public interface EventRequestService {

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     *
     * @param userId ID пользователя
     * @return список дто-экземпляров запросов на участие
     */
    List<EventRequestDto> getRequests(Long userId);

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр запроса на участие
     */
    EventRequestDto addRequest(Long userId, Long eventId);

    /**
     * Отмена своего запроса на участие в событии
     *
     * @param userId    ID пользователя
     * @param requestId ID события
     * @return дто-экземпляр запроса на участие
     */
    EventRequestDto cancelRequest(Long userId, Long requestId);
}
