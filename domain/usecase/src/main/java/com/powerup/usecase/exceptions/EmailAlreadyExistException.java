package com.powerup.usecase.exceptions;

/**
 * Exception thrown when attempting to register an email that already exists in the system.
 *
 * @version 1.0
 * @since 2025-08-31
 */
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
