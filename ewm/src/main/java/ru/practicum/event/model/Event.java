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
    private Long id;

    @Column(name = "title")
/*    @Max(120)
    @Min(3)*/
    private String title;

    @Column(name = "annotation")
/*    @Max(2000)
    @Min(20)*/
    private String annotation;

    @Column(name = "description")
/*    @Max(7000)
    @Min(20)*/
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "request_moderation")
    private Boolean requestModeration = true;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState state;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "event",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JsonIgnore
    Set<EventRequest> requests = new HashSet<>();

    private Integer confirmedRequests;

    public void incrementConfirmedRequests() {
        confirmedRequests++;
    }

    public void decrementConfirmedRequests() {
        confirmedRequests--;
    }
}
