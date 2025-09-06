package com.powerup.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RoutesProperties is a utility class that defines constant values for API route paths.
 * These constants are used throughout the application to ensure consistency and avoid
 * hardcoding route strings in multiple places.
 *
 * @version 1.0
 * @since 2025-08-23
 */
@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "routes")
public class RoutesProperties {

    private String authApiPath;
    private String userApiPath;

}
