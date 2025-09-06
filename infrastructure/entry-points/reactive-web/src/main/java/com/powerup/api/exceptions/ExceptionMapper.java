package com.powerup.api.exceptions;

import com.powerup.usecase.exceptions.EmailAlreadyExistException;
import com.powerup.usecase.exceptions.InvalidCredentialsException;
import com.powerup.usecase.exceptions.UserAuthNotFoundException;
import com.powerup.usecase.exceptions.UserAuthValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.powerup.api.exceptions.ExceptionConstants.*;

/**
 * Maps exceptions to corresponding HTTP status codes and error messages.
 * Provides a centralized way to handle different types of exceptions
 * and return appropriate responses.
 *
 * @version 1.0
 * @since 2025-08-31
 */
@Component
public class ExceptionMapper  {

    private static final Map<Class<? extends Throwable>, ErrorDefinition> ERROR_MAP = Map.of(
            InvalidCredentialsException.class, new ErrorDefinition(HttpStatus.UNAUTHORIZED, ERROR_INVALID_CREDENTIALS_MESSAGE),
            UserAuthValidationException.class, new ErrorDefinition(HttpStatus.BAD_REQUEST, ERROR_USER_VALIDATION_MESSAGE),
            UserAuthNotFoundException.class, new ErrorDefinition(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND_MESSAGE),
            EmailAlreadyExistException.class, new ErrorDefinition(HttpStatus.UNPROCESSABLE_ENTITY, ERROR_EMAIL_VALIDATION_MESSAGE)
    );

    public ErrorDefinition map(Throwable ex) {
        return ERROR_MAP.getOrDefault(ex.getClass(),
                new ErrorDefinition(HttpStatus.INTERNAL_SERVER_ERROR, GENERIC_ERROR_MESSAGE));
    }

    public record ErrorDefinition(HttpStatus status, String message) {}
}
