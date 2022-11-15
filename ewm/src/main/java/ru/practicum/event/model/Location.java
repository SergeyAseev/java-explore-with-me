package ru.practicum.event.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class Location {

    private Double lon;
    private Double lat;
}
