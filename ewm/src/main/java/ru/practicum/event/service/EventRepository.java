package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    //находим все события инициатора
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    //находим событие инициатора
    Event findByIdAndInitiatorId(Long eventId, Long userId);

    //вернуть все события в подборки
    Set<Event> findAllByIdIn(Set<Long> events);

    // находим событие по статусу
    Optional<Event> findByIdAndState(Long eventId, EventState state);

    //находим события по параметрам для админа
    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:catIds) IS NULL OR e.category.id IN :catIds) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd)")
    List<Event> searchEvents(List<Long> users, List<EventState> states,
                             List<Integer> catIds, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);

    //находим события по параметрам для пользователей
    @Query("select e from Event e where (lower(e.annotation) like lower(concat('%', :text, '%')) or " +
            "lower(e.description) like lower(concat('%', :text, '%')))" +
            "AND ((:catIds) IS NULL OR e.category.id IN :catIds) " +
            "and e.paid = :isPaid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and (:onlyAvailable is false or (e.participantLimit = 0 or e.confirmedRequests < e.participantLimit))")
    List<Event> findEvents(String text, List<Integer> catIds, Boolean isPaid, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, Boolean onlyAvailable);
}
