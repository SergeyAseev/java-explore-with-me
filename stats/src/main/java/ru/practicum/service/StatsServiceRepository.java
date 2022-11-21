package ru.practicum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.dto.ViewDto;
import ru.practicum.model.EndPointStatsClient;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServiceRepository extends JpaRepository<EndPointStatsClient, Long> {



/*    List<ViewDto> countTotalIpUnique(LocalDateTime startDate, LocalDateTime endDate, List<String> uriIds);


    List<ViewDto> countTotalIp(LocalDateTime startDate, LocalDateTime endDate, List<String> uriIds);*/
}
