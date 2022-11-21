package ru.practicum.dto;

import ru.practicum.model.EndPointStatsClient;

public class EndPointStatsClientMapper {

    private static EndPointStatsClientDto toEndPointStatsClientDto (EndPointStatsClient endPointStatsClient) {
        return EndPointStatsClientDto.builder()
                .ip(endPointStatsClient.getIp())
                .app(endPointStatsClient.getApp())
                .uri(endPointStatsClient.getUri())
                .ip(endPointStatsClient.getIp())
                .build();
    }

    public static EndPointStatsClient toEndPointStatsClient(EndPointStatsClientDto endPointStatsClientDto) {
        return EndPointStatsClient.builder()
                .app(endPointStatsClientDto.getApp())
                .uri(endPointStatsClientDto.getUri())
                .ip(endPointStatsClientDto.getIp())
                .timestamp(endPointStatsClientDto.getTimestamp())
                .build();
    }
}
