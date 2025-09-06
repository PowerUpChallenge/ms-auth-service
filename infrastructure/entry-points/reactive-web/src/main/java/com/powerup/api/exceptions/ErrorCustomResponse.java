package com.powerup.api.exceptions;

/**
 * A record representing a custom error response.
 *
 * @param error  the error message
 * @param status the HTTP status code
 *
 * @version 1.0
 * @since 2025-08-31
 */
public record ErrorCustomResponse(
        String error,
        int status
) {}
