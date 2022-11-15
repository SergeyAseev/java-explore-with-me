package ru.practicum.event.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
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
                                             @RequestParam(name = "states", required = false) List<EventState> stateIds,
                                             @RequestParam(name = "categories", required = false) List<Integer> catIds,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Retrieve events");
        return eventService.retrieveEvents(userIds, stateIds, catIds, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("{eventId}")
    public EventFullDto updateEvent(@PathVariable @NonNull Long eventId,
                                    @RequestBody NewEventDto newEventDto) {
        log.info("Update event = {}", newEventDto);
        return eventService.updateEvent(newEventDto, eventId);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @NonNull Long eventId) {
        log.info("Publish the event with ID = {}", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @NonNull Long eventId) {
        log.info("Reject the event with ID = {}", eventId);
        return eventService.rejectEvent(eventId);
    }

}
