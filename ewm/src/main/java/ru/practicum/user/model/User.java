package ru.practicum.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID пользователя

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email; // эл.почта пользователя

    @NotBlank
    @Size(max = 256)
    @Column(name = "name", nullable = false)
    private String name; // имя пользователя

}
