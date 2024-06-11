package ru.practicum.ewm.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс класса-репозитория Hit
 */
public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(e.id)) " +
            " from Hit as e " +
            " where e.hitDate between :start and :end " +
            " group by e.app, e.uri " +
            " order by count(e.id) desc ")
    List<Stat> getHits(@Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(distinct e.ipAddress)) " +
            " from Hit as e " +
            " where e.hitDate between :start and :end " +
            " group by e.app, e.uri " +
            " order by count(distinct e.ipAddress) desc ")
    List<Stat> getUniqueHits(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(e.id)) " +
            " from Hit as e " +
            " where e.uri in :uris " +
            "   and e.hitDate between :start and :end " +
            " group by e.app, e.uri " +
            " order by count(e.id) desc ")
    List<Stat> getHitsByUris(@Param("uris") List<String> uris,
                             @Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.model.Stat(e.app, e.uri, count(distinct e.ipAddress)) " +
            " from Hit as e " +
            " where e.uri in :uris " +
            "   and e.hitDate between :start and :end " +
            " group by e.app, e.uri " +
            " order by count(distinct e.ipAddress) desc ")
    List<Stat> getUniqueHitsByUris(@Param("uris") List<String> uris,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);
}