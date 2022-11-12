package ru.practicum.eventRequest.dto;

import lombok.*;
import ru.practicum.eventRequest.model.RequestState;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

    private Long id;
    private Long eventId;
    private Long userId;
    private LocalDateTime created;
    private RequestState status;
}
