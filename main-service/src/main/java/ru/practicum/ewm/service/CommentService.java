package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CommentDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса COMMENT
 */
public interface CommentService {

    CommentDto createComment(Long userId, Long eventId, CommentDto commentDto);

    List<CommentDto> readAllCommentsByEventId(Long eventId, Integer from, Integer size);

    CommentDto updateCommentById(Long commentId, Long userId, CommentDto commentDto);

    void deleteCommentByAdmin(Long commentId);

    void deleteCommentByUser(Long commentId, Long userId);
}