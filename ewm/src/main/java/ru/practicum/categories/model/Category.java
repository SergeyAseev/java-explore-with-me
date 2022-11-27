package ru.practicum.categories.model;

import lombok.*;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // ID категории

    @NotBlank
    @Column(name = "name")
    private String name; // название категории

    @OneToMany(mappedBy = "category")
    private Set<Event> events; //список событий данной категории
}
