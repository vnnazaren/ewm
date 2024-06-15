package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.exceptions.AccessException;
import ru.practicum.ewm.exceptions.ConflictException;
import ru.practicum.ewm.exceptions.EntityNotFoundException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.CommentService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.RequestService;
import ru.practicum.ewm.service.UserService;
import ru.practicum.ewm.storage.CommentRepository;
import ru.practicum.ewm.util.EventStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;
    private final RequestService requestService;

    public CommentDto createComment(Long userId, Long eventId, CommentDto commentDto) {
        User user = UserMapper.toUser(userService.readUser(userId));
        Event event = EventMapper.toEvent(eventService.readEvent(eventId));

        requestService.checkRequestByUserIdAndEventId(userId, eventId);

        if (!Objects.equals(event.getState(), EventStatus.PUBLISHED)) {
            throw new ConflictException("Статус события должен быть PUBLISHED.");
        }

        Optional<Comment> foundComment = commentRepository.findByEventIdAndAuthorId(eventId, userId);
        if (foundComment.isPresent()) {
            throw new AccessException(String.format("Пользователь с ID %s уже оставлял комментарий к событию с ID %s.",
                    userId, eventId));
        }

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setAuthor(user);
        comment.setEvent(event);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public Comment readComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new EntityNotFoundException(String.format("Комментарий с ID %s не найден.",
                    commentId));
        }
    }

    @Transactional(readOnly = true)
    public List<CommentDto> readAllCommentsByEventId(Long eventId, Integer from, Integer size) {
        eventService.readEvent(eventId);

        Pageable page = PageRequest.of(from, size);
        List<Comment> comments = commentRepository.findAllByEventIdOrderByCreatedDesc(eventId, page);

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public CommentDto updateCommentById(Long commentId, Long userId, CommentDto commentDto) {
        Comment comment = readComment(commentId);

        checkUserIsAuthorComment(comment.getAuthor().getId(), userId, commentId);

        String newText = commentDto.getText();
        if (StringUtils.hasLength(newText)) {
            comment.setText(newText);
        }

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(savedComment);
    }

    public void deleteCommentByAdmin(Long commentId) {
        readComment(commentId);
        commentRepository.deleteById(commentId);
    }

    public void deleteCommentByUser(Long commentId, Long userId) {
        Comment comment = readComment(commentId);

        checkUserIsAuthorComment(comment.getAuthor().getId(), userId, commentId);

        commentRepository.deleteById(commentId);
    }

    private void checkUserIsAuthorComment(Long authorId, Long userId, Long commentId) {
        if (!authorId.equals(userId)) {
            throw new AccessException(String.format(
                    "Пользователь с ID %s не является автором комментария с ID %s.",
                    userId, commentId));
        }
    }
}