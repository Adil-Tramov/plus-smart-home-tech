package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAuthorizedUserException extends ErrorResponse {
    public NotAuthorizedUserException(String username) {
        super(HttpStatus.UNAUTHORIZED,
                "Имя пользователя не должно быть пустым. Передано имя: " + "'" + username + "'");
    }
}
