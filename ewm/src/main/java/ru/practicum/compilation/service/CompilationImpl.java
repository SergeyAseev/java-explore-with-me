package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationMapper;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationImpl implements CompilationService {

    @Autowired
    final CompilationRepository compilationRepository;

    @Autowired
    final EventRepository eventRepository;

    @Override
    public List<CompilationDto> retrieveAllCompilations(Boolean pinned, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size);
        if (Objects.isNull(pinned)) {
            compilationRepository.findAll(pageable);
        }

        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(CompilationMapper::toCompilDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {

        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilationRepository.save(compilation);

        compilation.getEvents().stream()
                .map(e -> addEventInCompilation(compilation.getId(), e.getId()))
                .collect(Collectors.toList());

        return CompilationMapper.toCompilDto(compilation);
    }

    @Override
    public void removeCompilationById(Integer compId) {

        retrieveCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public void removeEventFromCompilation(Integer compId, Long eventId) {

        Compilation compilation = retrieveCompilationById(compId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with ID %s was not found", eventId)));

        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto addEventInCompilation(Integer compId, Long eventId) {

        Compilation compilation = retrieveCompilationById(compId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with ID %s was not found", eventId)));

        Set<Event> events = compilation.getEvents();
        events.add(event);
        return CompilationMapper.toCompilDto(compilationRepository.save(compilation));
    }

    @Override
    public void pinnedOutCompilation(Integer compId) {

        Compilation compilation = retrieveCompilationById(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto pinnedCompilation(Integer compId) {

        Compilation compilation = retrieveCompilationById(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);

        return CompilationMapper.toCompilDto(compilation);
    }

    @Override
    public CompilationDto retrieveDtoCompilationById(Integer compId) {

        return CompilationMapper.toCompilDto(compilationRepository
                .findById(compId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Compilation with ID %s was not found", compId))));
    }

    /**
     * Метод получение НЕ дто экземпляра подборки
     *
     * @param compId ID подборки
     * @return экземпляр подборки
     */
    public Compilation retrieveCompilationById(Integer compId) {

        return compilationRepository
                .findById(compId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Compilation with ID %s was not found", compId)));
    }
}
