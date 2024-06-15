package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.service.CommentService;

import javax.validation.Valid;

/**
 * Класс-контроллер COMMENT
 * Закрытый API для работы с комментариями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
public class PrivateCommentsController {
    private final CommentService commentService;

    /**
     * Добавление нового комментария
     *
     * @param userId     Id текущего пользователя
     * @param eventId    Id события
     * @param commentDto DTO класса COMMENT для создания нового комментария
     * @return Коллекция объектов DTO с комментариями
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable Long userId,
                                    @RequestParam Long eventId,
                                    @Valid @RequestBody CommentDto commentDto) {
        log.info("PrivateCommentsController: POST /users/{}/comments?eventId={} - {}", userId, eventId, commentDto);
        return commentService.createComment(userId, eventId, commentDto);
    }

    /**
     * Изменение комментария добавленного текущим пользователем.
     *
     * @param userId    Id текущего пользователя
     * @param commentId Id комментария
     * @return Объект DTO комментария
     */
    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable Long userId,
                                    @PathVariable Long commentId,
                                    @Valid @RequestBody CommentDto commentDto) {
        log.info("PrivateCommentsController: PATCH /users/{}/comments/{} - {}", userId, commentId, commentDto);
        return commentService.updateCommentById(commentId, userId, commentDto);
    }

    /**
     * Удаление комментария добавленного текущим пользователем.
     *
     * @param userId    Id текущего пользователя
     * @param commentId Id комментария
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        log.info("PrivateCommentsController: DELETE /users/{}/comments/{}",
                userId, commentId);
        commentService.deleteCommentByUser(commentId, userId);
    }
}