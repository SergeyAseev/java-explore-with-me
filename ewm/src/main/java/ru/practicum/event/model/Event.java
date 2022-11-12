package ru.practicum.event.model;

import lombok.*;
import ru.practicum.categories.model.Category;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

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
    private Long id;

    @Column(name = "title")
    @Max(120)
    @Min(3)
    private String title;

    @Column(name = "annotation")
    @Max(2000)
    @Min(20)
    private String annotation;

    @Column(name = "description")
    @Max(7000)
    @Min(20)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @ManyToOne
    @Column(name = "initiator_id")
    private User initiatorId;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState eventState;

    @Embedded
    private Location location;

}
