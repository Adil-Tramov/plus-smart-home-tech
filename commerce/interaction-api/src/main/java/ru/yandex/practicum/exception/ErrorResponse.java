package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public abstract class ErrorResponse extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String userMessage;

    public ErrorResponse(HttpStatus httpStatus, String userMessage) {
        super(userMessage);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
