package ru.practicum.eventRequest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_requests")
public class EventRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //ID запроса на участие

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // участник события

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // событие

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created; // дата создания запроса на участие

    @Enumerated(EnumType.STRING)
    private RequestState status; // статус запроса на участие
}
