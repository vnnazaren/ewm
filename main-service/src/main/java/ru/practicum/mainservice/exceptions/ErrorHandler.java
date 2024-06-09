package ru.practicum.mainservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        log.info("HttpStatus.BAD_REQUEST (400). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .reason("Bad request.")
                        .status(HttpStatus.BAD_REQUEST.name())
                        .timestamp(LocalDateTime.now().format(FORMAT))
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        log.info("HttpStatus.NOT_FOUND (404). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .reason("Not found.")
                        .status(HttpStatus.NOT_FOUND.name())
                        .timestamp(LocalDateTime.now().format(FORMAT))
                        .build());
    }

    @ExceptionHandler({DataIntegrityViolationException.class,
            WrongParameterException.class})
    public ResponseEntity<ErrorResponse> handleWrongParameterException(Exception e) {
        log.info("HttpStatus.CONFLICT (409). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .reason("Conflict.")
                        .status(HttpStatus.CONFLICT.name())
                        .timestamp(LocalDateTime.now().format(FORMAT))
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable t) {
        log.info("HttpStatus.INTERNAL_SERVER_ERROR (500). {}", t.getMessage(), t);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .message(t.getMessage())
                        .reason("Internal server error.")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .timestamp(LocalDateTime.now().format(FORMAT))
                        .build());
    }
}