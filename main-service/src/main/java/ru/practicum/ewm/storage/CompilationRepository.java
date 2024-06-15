package ru.practicum.ewm.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.Compilation;

import java.util.List;

/**
 * Интерфейс класса-репозитория COMPILATION
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("select c " +
            "from Compilation as c " +
            "where (:pinned is null or :pinned = c.pinned)") // попробовать переделать на спецификации
    List<Compilation> findAllByPinned(@Param("pinned") Boolean pinned,
                                      Pageable page);
}