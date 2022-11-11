package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    @Autowired
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> retrieveEvents(@RequestParam(name = "users", required = false) List<Long> userIds,
                                             @RequestParam(name = "states", required = false) List<String> stateIds,
                                             @RequestParam(name = "categories", required = false) List<Integer> catIds,
                                             @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Retrieve events");
        return eventService.retrieveEvents();
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent (@PathVariable Long eventId,
                                     @RequestBody EventFullDto eventFullDto) {
        log.info("Update event = {}", eventFullDto);
        return eventService.updateEvent(eventFullDto, eventId);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent (@PathVariable Long eventId) {
        log.info("Publish the event with ID = {}", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent (@PathVariable Long eventId) {
        log.info("Reject the event with ID = {}", eventId);
        return eventService.rejectEvent(eventId);
    }

}
