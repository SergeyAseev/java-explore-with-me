package ru.practicum.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortDto {

    private Long id;

    @NotBlank
    @Size(max = 256)
    private String name;
}
