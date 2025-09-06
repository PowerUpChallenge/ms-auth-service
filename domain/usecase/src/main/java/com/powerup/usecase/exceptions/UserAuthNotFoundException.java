package com.powerup.usecase.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 *
 * @version 1.0
 * @since 2025-08-31
 */
public class UserAuthNotFoundException extends RuntimeException {
    public UserAuthNotFoundException(String message) {
        super(message);
    }
}
