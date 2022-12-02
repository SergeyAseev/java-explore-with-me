package ru.practicum.service;

import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.model.ViewStats;

import java.util.List;

public interface StatsService {

    /**
     * @return
     */
    EndPointStatsClientDto save(EndPointStatsClientDto endPointStatsClientDto);


    /**
     * @param startDate
     * @param endDate
     * @param uriIds
     * @param unique
     * @return
     */
    List<ViewStats> getViewStats(String startDate, String endDate, List<String> uriIds, Boolean unique);
}
