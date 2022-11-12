package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.categories.model.Category;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EventShortDto {

    private String annotation;
    private Category category;
    //private confirmedRequests;
    private LocalDateTime eventDate;
    private Long id;
    private User initiatorId;
    private Boolean paid;
    private String title;
    //private views;
}
