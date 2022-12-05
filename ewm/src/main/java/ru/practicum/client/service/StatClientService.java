package ru.practicum.client.service;

import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Map;

public interface StatClientService {

    /**
     * Получаем число просмотров событий из списка
     *
     * @param events экземпляр сущности события
     * @param unique учет.уникальности
     * @return число просмотров событий для каждого события в списке
     */
    Map<Long, Long> getViewsForEvents(List<Event> events, Boolean unique);

    /**
     * Получаем число просмотров для одного события
     *
     * @param event  экземпляр сущности события
     * @param unique учет.уникальности
     * @return число просмотров события
     */
    Long getViewsForEvent(Event event, Boolean unique);

}
