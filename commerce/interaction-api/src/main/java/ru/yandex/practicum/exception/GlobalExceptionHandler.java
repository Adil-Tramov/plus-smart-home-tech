package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ErrorResponse.class})
    public ResponseEntity<ErrorResponse> handleException(ErrorResponse errorResponse) {
        log.error(errorResponse.getUserMessage(), errorResponse);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
