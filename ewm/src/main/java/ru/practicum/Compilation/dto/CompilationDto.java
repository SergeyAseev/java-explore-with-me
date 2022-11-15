package ru.practicum.Compilation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Integer id;
    private Boolean pinned;
    private String title;
    private Set<EventShortDto> events;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventShortDto {

        private String annotation;
        private CategoryDto category;
        private Integer confirmedRequests;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        private Long id;
        private UserShortDto initiator;
        private Boolean paid;
        private String title;
        //private Long views;

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserShortDto {
            private Long id;
            private String name;
        }

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CategoryDto {
            private Integer id;
            private String name;
        }
    }

}
