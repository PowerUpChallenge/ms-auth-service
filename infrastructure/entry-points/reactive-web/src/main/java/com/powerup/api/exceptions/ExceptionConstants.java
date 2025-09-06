package com.powerup.api.exceptions;

/**
 * A utility class that holds constant error message strings used throughout the application.
 *
 * @version 1.0
 * @since 2025-08-31
 */
public class ExceptionConstants {

    public static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred";
    public static final String ERROR_INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";
    public static final String ERROR_USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String ERROR_USER_VALIDATION_MESSAGE = "Some fields are invalid";
    public static final String ERROR_EMAIL_VALIDATION_MESSAGE = "Email is already registered";

    private ExceptionConstants(){
    }
}
