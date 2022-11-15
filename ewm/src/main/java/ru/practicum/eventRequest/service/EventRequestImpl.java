package ru.practicum.eventRequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventRepository;
import ru.practicum.eventRequest.dto.EventRequestDto;
import ru.practicum.eventRequest.dto.EventRequestMapper;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.eventRequest.model.RequestState;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %s not found", userId)));

        List<EventRequest> eventRequests = eventRequestRepository.findAllByUser(user);

        return eventRequests.stream()
                .map(EventRequestMapper::toEventRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestDto addRequest(Long userId, Long eventId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %s not found", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with ID %s not found", eventId)));

        EventRequest eventRequest = EventRequest.builder()
                .event(event)
                .user(user)
                .created(LocalDateTime.now())
                .status(RequestState.PENDING)
                .build();


        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Initiator can't be added into eventRequest");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Event has to be PUBLISHED");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            eventRequest.setStatus(RequestState.CONFIRMED);
            //TODO increase particLimit
        }

        eventRequestRepository.save(eventRequest);

        return EventRequestMapper.toEventRequestDto(eventRequest);
    }

    @Override
    public EventRequestDto cancelRequest(Long userId, Long requestId) {

        EventRequest eventRequest = eventRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("EventRequest with ID = %s not found", requestId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %s not found", userId)));

        eventRequest.setStatus(RequestState.CANCELED);
        //TODO decriase particLimit
        return EventRequestMapper.toEventRequestDto(eventRequestRepository.save(eventRequest));
    }
}
