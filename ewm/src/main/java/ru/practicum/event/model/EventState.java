package ru.practicum.event.model;

import java.util.Optional;

public enum EventState {

    PUBLISHED,
    CANCELED,
    PENDING;

    public static Optional<EventState> from(String stringEventState) {
        for (EventState eventState : values()) {
            if (eventState.name().equalsIgnoreCase(stringEventState)) {
                return Optional.of(eventState);
            }
        }
        return Optional.empty();
    }
}
