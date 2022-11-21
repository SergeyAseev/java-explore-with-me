package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.dto.EndPointStatsClientMapper;
import ru.practicum.dto.ViewDto;
import ru.practicum.model.EndPointStatsClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsServiceRepository statsServiceRepository;

    @Override
    public void addHit(EndPointStatsClientDto endPointStatsClientDto) {

        EndPointStatsClient endPointStatsClient = EndPointStatsClientMapper.toEndPointStatsClient(endPointStatsClientDto);
        statsServiceRepository.save(endPointStatsClient);
    }

    @Override
    public List<ViewDto> getViewStats(LocalDateTime startDate, LocalDateTime endDate, List<String> uriIds, Boolean unique) {

/*        if (unique) {
            return statsServiceRepository.countTotalIpUnique(startDate, endDate, uriIds);
        } else {
            return statsServiceRepository.countTotalIp(startDate, endDate, uriIds);
        }*/
        return new ArrayList<>();
    }
}
