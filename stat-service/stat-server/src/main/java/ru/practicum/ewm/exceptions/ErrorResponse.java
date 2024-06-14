package ru.practicum.ewm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Класс с сообщением об ошибке
 */
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    private String reason;

    private String status;

    private String timestamp;
}