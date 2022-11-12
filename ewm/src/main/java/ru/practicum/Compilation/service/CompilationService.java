package ru.practicum.Compilation.service;

import ru.practicum.Compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

    /**
     * @return
     */
    List<CompilationDto> retrieveAllCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * @param compId
     * @return
     */
    CompilationDto retrieveCompilationById(Integer compId);

    /**
     * @param compilationDto
     * @return
     */
    CompilationDto addNewCompilation(CompilationDto compilationDto);

    /**
     * @param compId
     */
    void removeCompilationById(Integer compId);

    /**
     * @param eventId
     * @param compId
     */
    void removeEventFromCompilation(Integer compId, Long eventId);

    /**
     * @param compId
     * @param eventId
     * @return
     */
    CompilationDto addEventInCompilation(Integer compId, Long eventId);

    /**
     * @param compId
     * @return
     */
    void pinnedOutCompilation(Integer compId);

    /**
     * @param compId
     * @return
     */
    CompilationDto pinnedCompilation(Integer compId);
}
