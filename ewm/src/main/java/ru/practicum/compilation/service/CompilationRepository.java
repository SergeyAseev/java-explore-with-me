package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    // найти все закрепленные/ не закрепленные подборки
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
