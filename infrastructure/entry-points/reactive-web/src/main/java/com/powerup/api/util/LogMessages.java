package com.powerup.api.util;

/**
 * LogMessages is a utility class that contains standardized log message templates
 * for various actions performed in the application. These messages are used to ensure
 * consistent logging across different components, making it easier to trace and debug
 * application behavior.
 *
 * @version 1.0
 * @since 2025-08-23
 */
public class LogMessages {

    /**UserAuthHandler*/
    public static final String SAVE_USER_START = "Endpoint: {} | Action: Save user | Starting user save";
    public static final String SAVE_USER_DATA = "Endpoint: {} | Action: Save user | Received data: {}";
    public static final String SAVE_USER_SUCCESS = "[Endpoint: {} | Action: Save user | User saved successfully.";
    public static final String SAVE_USER_ERROR = "Endpoint: {} | Action: Save user | Error saving user, cause: {}";

    public static final String GET_USER_START = "Endpoint: {} | Action: Get user by idNumber | Starting user retrieval for idNumber";
    public static final String GET_USER_SUCCESS = "Endpoint: {} | Action: Get user by idNumber | User retrieved successfully for idNumber: {}";
    public static final String GET_USER_ERROR = "Endpoint: {} | Action: Get user by idNumber | Error retrieving user for idNumber: {}, cause: {}";

    /**AuthenticationHandler*/
    public static final String AUTHENTICATE_USER_START = "Endpoint: {} | Action: Authenticate user | Starting authentication";
    public static final String AUTHENTICATE_USER_SUCCESS = "Endpoint: {} | Action: Authenticate user | Authentication successful for user: {}";
    public static final String AUTHENTICATE_USER_FAILURE = "Endpoint: {} | Action: Authenticate user | Authentication failed for user: {}";

    private LogMessages() {
    }
}