package ru.practicum.mainservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс с сообщением об ошибке
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String status;

    private String reason;

    private String message;

    private String timestamp;
}