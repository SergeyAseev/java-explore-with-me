package ru.practicum.eventRequest.dto;

import ru.practicum.event.model.Event;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.user.model.User;

public class EventRequestMapper {

    public static EventRequestDto toEventRequestDto(EventRequest eventRequest) {
        return EventRequestDto.builder()
                .id(eventRequest.getId())
                .requester(eventRequest.getUser().getId())
                .event(eventRequest.getEvent().getId())
                .status(eventRequest.getStatus())
                .created(eventRequest.getCreated())
                .build();
    }

    public static EventRequest toEventRequest(EventRequestDto eventRequestDto, User user, Event event) {
        return EventRequest.builder()
                .id(eventRequestDto.getId())
                .user(user)
                .event(event)
                .status(eventRequestDto.getStatus())
                .build();
    }
}
