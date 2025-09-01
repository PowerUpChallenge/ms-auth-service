package com.powerup.r2dbc.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for MySQL connection.
 * Maps properties with the prefix "adapters.r2dbc" from application configuration files.
 *
 * @param host     the database host
 * @param port     the database port
 * @param database the database name
 * @param username the database username
 * @param password the database password
 * @version 1.0
 * @since 2025-08-23
 */
@ConfigurationProperties(prefix = "adapters.r2dbc")
public record MysqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String username,
        String password) {
}
