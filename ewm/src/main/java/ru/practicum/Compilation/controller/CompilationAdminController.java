package ru.practicum.Compilation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Compilation.dto.CompilationDto;
import ru.practicum.Compilation.dto.NewCompilationDto;
import ru.practicum.Compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    @Autowired
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {

        log.info("Add new compilation {}", newCompilationDto);
        return compilationService.addNewCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    void removeCompilationById(@PathVariable @NonNull Integer compId) {

        log.info("Remove compilation with ID = {}", compId);
        compilationService.removeCompilationById(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    void removeEventFromCompilation(@PathVariable @NonNull Integer compId,
                                    @PathVariable @NonNull Long eventId) {

        log.info("Remove event with ID = {} from compilation with ID = {}", eventId, compId);
        compilationService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public CompilationDto addEventInCompilation(@PathVariable @NonNull Integer compId,
                                                @PathVariable @NonNull Long eventId) {

        log.info("Add event with ID = {} in compilation with ID = {}", eventId, compId);
        return compilationService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    void pinnedOutCompilation(@PathVariable @NonNull Integer compId) {

        log.info("Pinned out Compilation with ID = {}", compId);
        compilationService.pinnedOutCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public CompilationDto pinnedCompilation(@PathVariable @NonNull Integer compId) {

        log.info("Pinned Compilation with ID = {}", compId);
        return compilationService.pinnedCompilation(compId);
    }
}
