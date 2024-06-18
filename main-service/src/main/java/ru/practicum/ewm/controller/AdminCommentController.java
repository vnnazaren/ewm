package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.CommentService;

import javax.validation.constraints.Positive;

/**
 * Класс-контроллер COMMENT
 * Закрытый API для работы с комментариями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {
    private final CommentService commentService;

    /**
     * Удаление комментария добавленного текущим пользователем.
     *
     * @param commentId Id комментария
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAdmin(@PathVariable @Positive Long commentId) {
        log.info("AdminCommentController: GET /admin/comments/{}", commentId);
        commentService.deleteCommentByAdmin(commentId);
    }
}