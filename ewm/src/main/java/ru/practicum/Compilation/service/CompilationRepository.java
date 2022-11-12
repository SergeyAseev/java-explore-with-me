package ru.practicum.Compilation.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}
