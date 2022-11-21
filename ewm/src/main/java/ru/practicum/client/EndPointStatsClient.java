package ru.practicum.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EndPointStatsClient {

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;

    public EndPointStatsClient(String app, String uri, String ip) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
    }
}
