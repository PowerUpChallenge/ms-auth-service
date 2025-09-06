package com.powerup.usecase.exceptions;

/**
 * Exception thrown when user credentials are invalid during authentication.
 *
 * @version 1.0
 * @since 2025-08-31
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
