package ru.practicum.eventRequest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.user.model.User;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    List<EventRequest> findAllByUser(User user);

    List<EventRequest> findAllByEventId(Long eventId);
}
