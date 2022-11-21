package ru.practicum.service;

import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.dto.ViewDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    /**
     * @return
     */
    void addHit(EndPointStatsClientDto endPointStatsClientDto);


    /**
     *
     * @param startDate
     * @param endDate
     * @param uriIds
     * @param unique
     * @return
     */
    List<ViewDto> getViewStats(LocalDateTime startDate, LocalDateTime endDate, List<String> uriIds, Boolean unique);
}
