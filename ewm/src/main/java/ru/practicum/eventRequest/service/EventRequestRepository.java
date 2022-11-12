package ru.practicum.eventRequest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.eventRequest.model.EventRequest;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
}
