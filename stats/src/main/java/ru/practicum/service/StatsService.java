package ru.practicum.service;

import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.model.ViewStats;

import java.util.List;

public interface StatsService {

    /** Сохраняем факт просмотра/поиска события
     * @return
     */
    EndPointStatsClientDto save(EndPointStatsClientDto endPointStatsClientDto);


    /**Возвращаем просмотры для списка событий
     * @param startDate дата создания события
     * @param endDate текущая дата
     * @param uriIds откуда пришел запрос
     * @param unique уникальность
     * @return
     */
    List<ViewStats> getViewStats(String startDate, String endDate, List<String> uriIds, Boolean unique);
}
