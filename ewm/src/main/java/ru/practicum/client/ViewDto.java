package ru.practicum.client;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewDto {

    private String app;
    private String uri;
    private Long hits;
}
