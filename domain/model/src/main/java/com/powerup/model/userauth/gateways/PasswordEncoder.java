package com.powerup.model.userauth.gateways;

/**
 * Interface for password encoding and verification.
 * Provides methods to encode a raw password and to verify a raw password against an encoded hash.
 * @version 1.0
 * @since 2025-08-23
 */
public interface PasswordEncoder {
    String encode(String raw);
    boolean matches(String raw, String hash);
}
