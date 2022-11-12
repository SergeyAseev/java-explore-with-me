package ru.practicum.Compilation.dto;


import lombok.*;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Integer id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;

}
