package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

/*    List<Event> findAllByParams(List<Long> users, List<EventState> states, List<Integer> categories, Boolean paid,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, String text,
                                Integer from, Integer size);*/

    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Event findByIdAndInitiatorId(Long eventId, Long userId);

    Event findByIdAndState(Long id, EventState state);
}
