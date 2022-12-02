package ru.practicum.client;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class EndPointStatsClientMapper {
    public static EndPointStatsClient toEndpointHitDto(String endpoint, HttpServletRequest request) {
        return EndPointStatsClient.builder()
                .app(endpoint)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}