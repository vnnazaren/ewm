package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MissingRequestHeaderException.class,
            MissingServletRequestParameterException.class,
            ValidationException.class,
            IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleValidation(Exception e) {
        log.info("HttpStatus.BAD_REQUEST (400). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(EntityNotFoundException e) {
        log.info("HttpStatus.NOT_FOUND (404). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler({WrongParameter.class})
    public ResponseEntity<ErrorMessage> handleWrongParameter(Exception e) {
        log.info("HttpStatus.CONFLICT (409). {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleThrowable(final Throwable t) {
        log.info("HttpStatus.INTERNAL_SERVER_ERROR (500). {}", t.getMessage(), t);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(t.getMessage()));
    }
}