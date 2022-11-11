package ru.practicum.event.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
