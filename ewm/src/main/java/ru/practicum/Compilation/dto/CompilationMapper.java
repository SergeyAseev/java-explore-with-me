package ru.practicum.Compilation.dto;

import ru.practicum.Compilation.model.Compilation;
import ru.practicum.event.model.Event;

import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toCompilDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents()
                        .stream()
                        .map(CompilationMapper::toEventShortDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    private static CompilationDto.EventShortDto toEventShortDto(Event event) {
        return CompilationDto.EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CompilationDto.EventShortDto.CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                //.confirmedRequests(event.getRequests().size())
                .eventDate(event.getEventDate())
                .initiator(CompilationDto.EventShortDto.UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                //.views(event.getViews())
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .events(newCompilationDto.getEvents()
                        .stream()
                        .map(e -> Event.builder().id(e).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
