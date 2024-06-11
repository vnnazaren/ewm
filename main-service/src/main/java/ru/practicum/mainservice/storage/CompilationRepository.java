package ru.practicum.mainservice.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.model.Compilation;

import java.util.List;

/**
 * Интерфейс класса-репозитория COMPILATION
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

//    @Query("select c " +
//            "from Compilation as c " +
//            "where (:pinned is null or :pinned = c.pinned)")
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}