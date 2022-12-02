package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndPointStatsClientDto;
import ru.practicum.model.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {

    @Autowired
    private final StatsService statsService;

    @PostMapping("/hit")
    public EndPointStatsClientDto save(@Valid @RequestBody EndPointStatsClientDto endPointStatsClientDto) {
        System.out.println("TEST_TEST");
        log.info("add event in statistic");
        return statsService.save(endPointStatsClientDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false")
            Boolean unique) {

        log.info("retrieve views");
        System.out.println("TEST_TEST");
        return statsService.getViewStats(start, end, uris, unique);
    }
}
