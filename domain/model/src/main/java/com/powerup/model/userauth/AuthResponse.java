package com.powerup.model.userauth;

/**
 * Response class for authentication containing the JWT token and its type.
 * @version 1.0
 * @since 2025-08-23
 */
public class AuthResponse {
    private final String accessToken;
    private final String tokenType;

    public AuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }

}
