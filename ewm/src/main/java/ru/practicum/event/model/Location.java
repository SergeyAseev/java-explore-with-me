package ru.practicum.event.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Location {

    Double lon;
    Double lat;
}
