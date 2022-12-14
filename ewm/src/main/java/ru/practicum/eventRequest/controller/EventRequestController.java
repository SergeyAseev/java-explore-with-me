package ru.practicum.eventRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.eventRequest.dto.EventRequestDto;
import ru.practicum.eventRequest.service.EventRequestService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class EventRequestController {

    @Autowired
    private final EventRequestService eventRequestService;

    @GetMapping
    public List<EventRequestDto> getRequests(@PathVariable Long userId) {
        log.info("Get all requests for user with ID = {}", userId);
        return eventRequestService.getRequests(userId);
    }

    @PostMapping
    public EventRequestDto addRequest(@PathVariable @NotNull Long userId,
                                      @RequestParam Long eventId) {
        log.info("Create request for user with ID = {} for event with ID = {}", userId, eventId);
        return eventRequestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public EventRequestDto cancelRequest(@PathVariable @NotNull Long userId,
                                         @PathVariable @NotNull Long requestId) {
        log.info("Cancel request with ID = {}", requestId);
        return eventRequestService.cancelRequest(userId, requestId);
    }
}
