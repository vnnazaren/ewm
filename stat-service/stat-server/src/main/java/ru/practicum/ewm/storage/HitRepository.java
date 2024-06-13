package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-репозитория HIT
 */
public interface HitRepository extends JpaRepository<Hit, Long> {

    // тупо все
    @Query(value = "select new ru.practicum.ewm.model.Stat( h.app, h.uri, count(distinct h.ipAddress)) " +
            "from Hit as h " +
            "where h.hitDate between :start and :end " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ipAddress) desc")
    List<Stat> getHits(@Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end);

    // тупо все и считаем уникальные IP
    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.ipAddress)) " +
            " from Hit as h " +
            " where h.hitDate between :start and :end " +
            " group by h.app, h.uri " +
            " order by count(h.ipAddress) desc ")
    List<Stat> getUniqueHits(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    // по списку URI
    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(distinct h.ipAddress)) " +
            " from Hit as h " +
            " where h.hitDate between :start and :end " +
            "   and h.uri in :uris " +
            " group by h.app, h.uri " +
            " order by count(distinct h.ipAddress) desc ")
    List<Stat> getHitsByUris(@Param("uris") List<String> uris,
                             @Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    // по списку URI и уникальные IP
    @Query(value = "select new ru.practicum.ewm.model.Stat(h.app, h.uri, count(h.ipAddress)) " +
            " from Hit as h " +
            " where h.hitDate between :start and :end " +
            "   and h.uri in :uris " +
            " group by h.app, h.uri " +
            " order by count(h.ipAddress) desc ")
    List<Stat> getUniqueHitsByUris(@Param("uris") List<String> uris,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}