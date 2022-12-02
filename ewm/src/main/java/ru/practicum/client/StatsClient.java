package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Value("${app-name}")
    private String appName;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void save(EndPointStatsClient endPointStatsClient) {
        post("/hit", endPointStatsClient);
    }

    public Long getViews(Long eventId) {

        String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        Map<String, Object> parameters = Map.of("start", URLEncoder.encode(LocalDateTime
                        .ofEpochSecond(0L, 0, ZoneOffset.UTC)
                        .format(dateTimeFormatter)), "end",
                URLEncoder.encode(LocalDateTime.now().format(dateTimeFormatter), StandardCharsets.UTF_8),
                "uris", (List.of("/events/" + eventId)), "unique", "false");
        ResponseEntity<Object> response = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                parameters);
        List<ViewStats> viewStatsList = (response.hasBody())
                ? (List<ViewStats>) response.getBody()
                : List.of();
        return (viewStatsList != null && !viewStatsList.isEmpty())
                ? viewStatsList.get(0).getHits()
                : 0L;
    }
}
