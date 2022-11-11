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
@RequestMapping("/users/{userId}/events")
public class EventPrivateController {

    @Autowired
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> retrieveEventsByCreator(@PathVariable Long userId,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Retrieve events by creator with ID = {}", userId);
        return eventService.retrieveEventsByCreator(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEventByCreator(@PathVariable Long userId,
                                             @RequestBody EventFullDto eventFullDto) {
        log.info("Update event {} by creator with ID = {}", eventFullDto, userId);
        return eventService.updateEventByCreator(userId, eventFullDto);
    }

    @PostMapping
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @RequestBody EventFullDto eventFullDto) {
        log.info("Create event {} by creator with ID = {}", eventFullDto, userId);
        return eventService.createEvent(userId, eventFullDto);
    }

    @GetMapping("/{eventId}")
    public List<EventFullDto> retrieveEventsByIdForCreator(@PathVariable Long userId,
                                                           @PathVariable Long eventId) {
        log.info("Retrieve event with ID = {}", eventId);
        return eventService.retrieveEventsByIdForCreator(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEventByCreator(@PathVariable Long userId,
                                             @PathVariable Long eventId) {
        log.info("Cancel event with ID = {} by creator", eventId);
        return eventService.cancelEventByCreator(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<EventFullDto> retrieveRequestEventByUser(@PathVariable Long userId,
                                                         @PathVariable Long eventId) {
        log.info("Retrieve info for event's request by user with ID = {} for event with ID = {}", userId, eventId);
        return eventService.retrieveRequestEventByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public EventFullDto confirmRequestForEvent(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        log.info("Confirm request for event with ID = {} for user with ID = {}", eventId, userId);
        return eventService.confirmRequestForEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public EventFullDto rejectRequestForEvent(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
        log.info("Reject request for event with ID = {} for user with ID = {}", eventId, userId);
        return eventService.rejectRequestForEvent(userId, eventId);
    }
}
