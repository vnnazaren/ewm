package ru.practicum.ewm.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.UriStat;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select h.app, h.uri, count(h.id) " +
            "from Event h " +
            "where h.uri in :uris " +
            "  and h.eventDate > :start " +
            "  and h.eventDate < :end " +
            "group by h.app, h.uri")
    List<UriStat> getHits(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query(value = "select h.app, h.uri, count(distinct h.ipAddress) " +
            "from Event h " +
            "where h.uri in :uris " +
            "  and h.eventDate > :start " +
            "  and h.eventDate < :end " +
            "group by h.app, h.uri")
    List<UriStat> getUniqueHits(List<String> uris, LocalDateTime start, LocalDateTime end);
}