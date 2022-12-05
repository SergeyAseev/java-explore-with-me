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
     * Поиск событий
     *
     * @return список дто-экземпляров событий
     */
    List<EventFullDto> retrieveEvents(List<Long> userIds, List<EventState> stateIds, List<Integer> catIds,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Редактирование события
     *
     * @param eventFullDto дто-экземпляр события
     * @param eventId      ID события
     * @return дто-экземпляр отредактированного события
     */
    EventFullDto updateEvent(NewEventDto eventFullDto, Long eventId);

    /**
     * Публикация события
     *
     * @param eventId ID события
     * @return
     */
    EventFullDto publishEvent(Long eventId);

    /**
     * Отклонение события
     *
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventFullDto rejectEvent(Long eventId);
    //admin controller end

    //public controller start

    /**
     * Получение событий с возможностью фильтрации
     *
     * @return список дто-экземпляров событий
     */
    List<EventShortDto> retrievePublicEvents(String text, List<Integer> catIds, Boolean paid, LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size);


    /**
     * Получение подробной информации об опубликованном событии по его ID
     *
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventFullDto retrievePublicEventById(Long eventId);
    //public controller end

    //private controller start

    /**
     * Получение событий,добавленных текущим пользователем
     *
     * @param userId ID пользователя
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return список дто-экземпляров событий
     */
    List<EventShortDto> retrieveEventsByCreator(Long userId, Integer from, Integer size);

    /**
     * Изменения события добавленного текущим пользователем
     *
     * @param userId       ID пользователя
     * @param eventFullDto дто-экземпляр события
     * @return дто-экземпляр события
     */
    EventFullDto updateEventByCreator(Long userId, NewEventDto eventFullDto);

    /**
     * Добавление нового события
     *
     * @param userId       ID пользователя
     * @param eventFullDto дто-экземпляр события
     * @return дто-экземпляр события
     */
    EventFullDto createEvent(Long userId, NewEventDto eventFullDto);

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventFullDto retrieveEventByIdForCreator(Long userId, Long eventId);

    /**
     * Отмена события добавленного текущим пользователем
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventFullDto cancelEventByCreator(Long userId, Long eventId);

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    List<EventRequestDto> retrieveRequestEventByUser(Long userId, Long eventId);

    /**
     * Подтверждение чужой завяки на участие в событии текущего пользователя
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventRequestDto confirmRequestForEvent(Long userId, Long eventId, Long reqId);

    /**
     * Отклоненик чужой завяки на участие в событии текущего пользователя
     *
     * @param userId  ID пользователя
     * @param eventId ID события
     * @return дто-экземпляр события
     */
    EventRequestDto rejectRequestForEvent(Long userId, Long eventId, Long reqId);

    //private controller end
}
