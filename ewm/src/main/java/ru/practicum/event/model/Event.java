package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.practicum.categories.model.Category;
import ru.practicum.eventRequest.model.EventRequest;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID события

    @Column(name = "title")
    private String title; // заголовок события

    @Column(name = "annotation")
    private String annotation; // аннотация события

    @Column(name = "description")
    private String description; // описание события

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // категория события

    @Column(name = "event_date")
    private LocalDateTime eventDate; //дата события

    @Column(name = "paid")
    private Boolean paid; // платное или нет

    @Column(name = "request_moderation")
    private Boolean requestModeration = true; // требуется ли модерация

    @Column(name = "participant_limit")
    private Integer participantLimit; // максимальное число участников

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator; // инициатор события

    @Column(name = "created_on")
    private LocalDateTime createdOn; // дата создания события

    @Column(name = "published_on")
    private LocalDateTime publishedOn; // дата публикации события

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState state; // статус события

    @Embedded
    private Location location; // локация события

    @OneToMany(mappedBy = "event",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EventRequest> requests = new HashSet<>(); // заявки на участие в событии

    private Integer confirmedRequests; // число потвержденных заявок

}
