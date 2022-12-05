package ru.practicum.event.model;

import java.util.Optional;

public enum Sort {

    EVENT_DATE,
    VIEWS;

    public static Optional<Sort> from(String stringSort) {
        for (Sort sort : values()) {
            if (sort.name().equalsIgnoreCase(stringSort)) {
                return Optional.of(sort);
            }
        }
        return Optional.of(EVENT_DATE);
    }
}
