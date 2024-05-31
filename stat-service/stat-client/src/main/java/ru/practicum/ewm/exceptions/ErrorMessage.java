package ru.practicum.ewm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс с сообщением об ошибке
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {
    private final String error;
}