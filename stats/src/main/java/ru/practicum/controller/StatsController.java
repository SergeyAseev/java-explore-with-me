package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.dto.ViewDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {

    @Autowired
    private final StatsService statsService;

    @PostMapping("/hit")
    public void addHit(@Valid @RequestBody EndPointStatsClientDto endPointStatsClientDto) {

        log.info("");//TODO
        statsService.addHit(endPointStatsClientDto);
    }

    @GetMapping("/stats")
    public List<ViewDto> getViewStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false) List<String> uriIds,
            @RequestParam(required = false, defaultValue = "false")
            Boolean unique) {

        log.info("");//TODO
        return statsService.getViewStats(startDate, endDate, uriIds, unique);
    }
}
