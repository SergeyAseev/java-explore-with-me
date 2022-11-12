package ru.practicum.Compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Compilation.dto.CompilationDto;
import ru.practicum.Compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationPublicController {

    @Autowired
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> retrieveAllCompilations(@RequestParam Boolean pinned,
                                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Retrieve all compilations");
        return compilationService.retrieveAllCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto retrieveCompilationById(@PathVariable Integer compId) {

        log.info("Retrieve compilation by ID = {}", compId);
        return compilationService.retrieveCompilationById(compId);
    }
}
