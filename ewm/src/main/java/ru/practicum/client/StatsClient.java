package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsClient {

    private final WebClient webClient;

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
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", String.join(",", uris).replace("{","")
                                .replace("}",""))
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(ViewStats.class)
                .collectList()
                .block();
    }

}