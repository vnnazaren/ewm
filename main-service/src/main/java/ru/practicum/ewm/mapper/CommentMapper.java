package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .eventId(comment.getEvent().getId())
                .authorId(comment.getAuthor().getId())
                .text(comment.getText())
                .createdOn(comment.getCreated())
                .build();
    }

    public Comment toComment(CommentDto commentDto) {
        return Comment.builder()
                .author(User.builder()
                        .id(commentDto.getId())
                        .build())
                .event(Event.builder()
                        .id(commentDto.getId())
                        .build())
                .text(commentDto.getText())
                .created(LocalDateTime.now())
                .build();
    }
}