package ru.practicum.Compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.Compilation.dto.CompilationDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationImpl implements CompilationService {

    @Autowired
    final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> retrieveAllCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto retrieveCompilationById(Integer compId) {
        return null;
    }

    @Override
    public CompilationDto addNewCompilation(CompilationDto compilationDto) {
        return null;
    }

    @Override
    public void removeCompilationById(Integer compId) {

    }

    @Override
    public void removeEventFromCompilation(Integer compId, Long eventId) {

    }

    @Override
    public CompilationDto addEventInCompilation(Integer compId, Long eventId) {
        return null;
    }

    @Override
    public void pinnedOutCompilation(Integer compId) {

    }

    @Override
    public CompilationDto pinnedCompilation(Integer compId) {
        return null;
    }
}
