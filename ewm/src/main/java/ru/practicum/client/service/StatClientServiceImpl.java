package ru.practicum.client.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.client.ViewStats;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatClientServiceImpl implements StatClientService {

    @Autowired
    private final StatsClient statsClient;

    private static String URI_EVENT = "/events/{%d}";

    public Map<Long, Long> getViewsForEvents(List<Event> events, Boolean unique) {

        Optional<Event> event = events.stream()
                .min(Comparator.comparing(Event::getEventDate));

        if (event.isEmpty()) {
            return new HashMap<>();
        }

        String start = event.get().getCreatedOn().toString();
        String end = LocalDateTime.now().toString();
        List<String> uris = events.stream()
                .map(e -> String.format(URI_EVENT, e.getId()))
                .collect(Collectors.toList());
        List<ViewStats> viewStats = statsClient.getViews(start, end, uris, unique);
        Map<Long, Long> eventViews = new HashMap<>();
        viewStats.forEach(e -> eventViews
                .put(getIdFromUri(e.getUri()), e.getHits()));

        return eventViews;
    }

    private Long getIdFromUri(String uri) {
        return Long.parseLong(StringUtils.getDigits(uri));
    }

    @Override
    public Long getViewsForEvent(Event event, Boolean unique) {

        List<ViewStats> views = statsClient.getViews(event.getCreatedOn().toString(),
                LocalDateTime.now().toString(),
                List.of(String.format(URI_EVENT, event.getId())),
                unique);

        if (views.isEmpty()) {
            return 0L;
        }
        return views.get(0).getHits();
    }
}
