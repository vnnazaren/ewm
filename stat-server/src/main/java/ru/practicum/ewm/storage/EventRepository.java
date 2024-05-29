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

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(e.id)) " +
            " from Event as e " +
            " where e.eventDate between :start and :end " +
            " group by e.app, e.uri")
    List<Stat> getHits(@Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(distinct e.ipAddress)) " +
            " from Event as e " +
            " where e.eventDate between :start and :end " +
            " group by e.app, e.uri")
    List<Stat> getUniqueHits(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(e.id)) " +
            " from Event as e " +
            " where e.uri in :uris " +
            "   and e.eventDate between :start and :end " +
            " group by e.app, e.uri")
    List<Stat> getHitsByUris(@Param("uris") List<String> uris,
                             @Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(distinct e.ipAddress)) " +
            " from Event as e " +
            " where e.uri in :uris " +
            "   and e.eventDate between :start and :end " +
            " group by e.app, e.uri")
    List<Stat> getUniqueHitsByUris(@Param("uris") List<String> uris,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}