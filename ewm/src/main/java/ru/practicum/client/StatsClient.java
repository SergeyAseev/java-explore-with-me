package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StatsClient {

    private final WebClient webClient;
    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .build();
    }

    @Value("${app-name}")
    private String appName;

    public void saveStats(HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        Stats stats = new Stats(appName, uri, ip);
        webClient.post()
                .uri("/hit")
                .body(BodyInserters.fromValue(stats))
                .retrieve()
                .bodyToMono(Stats.class)
                .block();
    }

    public List<ViewStats> getViews(String start, String end,
                                    List<String> uris, Boolean unique) {
        return webClient
                .get()
                //.uri(String.join("/stats?start={start}&end={end}&uris={uris}&unique={unique}"), start, end, uris, unique)
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", String.join(", ", uris).replace("{", "")
                                .replace("}", ""))
                        .queryParam("unique", unique)
                        .build())
                //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(ViewStats.class)
                .collectList()
                .block();
    }
}