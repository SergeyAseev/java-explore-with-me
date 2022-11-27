package ru.practicum.eventRequest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.user.model.User;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    //получаем все заявки на участие от текущего пользователя
    List<EventRequest> findAllByUser(User user);

    //получаем все заявки на участие в событии организатора
    List<EventRequest> findAllByEventId(Long eventId);
}
