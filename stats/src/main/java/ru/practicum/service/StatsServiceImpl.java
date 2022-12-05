package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.dto.EndPointStatsClientMapper;
import ru.practicum.model.EndPointStatsClient;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsServiceRepository statsServiceRepository;

    @Override
    public EndPointStatsClientDto save(EndPointStatsClientDto endPointStatsClientDto) {

        EndPointStatsClient endPointStatsClient = EndPointStatsClientMapper.toEndPointStatsClient(endPointStatsClientDto);
        endPointStatsClient.setTimestamp(LocalDateTime.now());
        return EndPointStatsClientMapper.toEndPointStatsClientDto(statsServiceRepository.save(endPointStatsClient));
    }

    @Override
    public List<ViewStats> getViewStats(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (unique) {
            return statsServiceRepository.findAllUnique(startTime, endTime, uris, unique);
        } else {
            return statsServiceRepository.findAll(startTime, endTime, uris);
        }
    }
}
