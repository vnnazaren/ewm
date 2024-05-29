package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-репозитория EVENT
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.id)) " +
            " from Event as h " +
            " where h.eventDate between :start and :end " +
            " group by h.app, h.uri"
    )
    List<Stat> getHits(@Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.id)) " +
            " from Event as h " +
            " where h.eventDate between :start and :end " +
            " group by h.app, h.uri, h.ipAddress"
    )
    List<Stat> getUniqueHits(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.id)) " +
            " from Event as h " +
            " where h.uri in :uris " +
            "   and h.eventDate between :start and :end " +
            " group by h.app, h.uri"
    )
    List<Stat> getHitsByUris(@Param("uris") List<String> uris,
                             @Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.id)) " +
            " from Event as h " +
            " where h.uri in :uris " +
            "   and h.eventDate between :start and :end " +
            " group by h.app, h.uri, h.ipAddress"
    )
    List<Stat> getUniqueHitsByUris(@Param("uris") List<String> uris,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}