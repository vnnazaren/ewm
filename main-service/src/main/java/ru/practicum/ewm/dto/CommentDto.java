package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс DTO класса "Комментарий" - COMMENT
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * Идентификатор категории
     */
    private Long id;

    /**
     * Дата и время создания комментария (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    /**
     * Идентификатор события
     */
    private Long eventId;

    /**
     * Идентификатор пользователя оставившего комментарий
     */
    private Long authorId;

    /**
     * Текст комментария
     */
    @Size(min = 5, max = 2000, message = "Длина названия категории должна быть от 5 до 2000 символов.")
    @NotBlank(message = "Комментарий должен быть указан и не должен содержать только пробелы.")
    private String text;
}