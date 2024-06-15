package ru.practicum.ewm.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventIdOrderByCreatedDesc(Long eventId, Pageable page);

    Optional<Comment> findByEventIdAndAuthorId(Long eventId, Long userId);
}