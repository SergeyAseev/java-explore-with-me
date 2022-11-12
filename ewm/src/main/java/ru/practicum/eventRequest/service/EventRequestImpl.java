package ru.practicum.eventRequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.event.service.EventRepository;
import ru.practicum.eventRequest.dto.EventRequestDto;
import ru.practicum.user.service.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRequestImpl implements EventRequestService {

    @Autowired
    private final EventRequestRepository eventRequestRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EventRepository eventRepository;

    @Override
    public List<EventRequestDto> getRequests(Long userId) {
        return null;
    }

    @Override
    public EventRequestDto addRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
