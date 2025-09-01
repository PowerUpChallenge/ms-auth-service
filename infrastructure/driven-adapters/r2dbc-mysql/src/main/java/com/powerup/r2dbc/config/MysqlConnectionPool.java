package com.powerup.r2dbc.config;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for setting up a MySQL connection pool using R2DBC.
 * This class defines the connection parameters and pool settings.
 * @version 1.0
 * @since 2025-08-23.
 */
@Configuration
public class MysqlConnectionPool {

    /** Initial size of the connection pool. */
    public static final int INITIAL_SIZE = 12;
    /** Maximum size of the connection pool. */
    public static final int MAX_SIZE = 15;
    /** Maximum idle time for connections in the pool (in minutes). */
    public static final int MAX_IDLE_TIME = 30;

    /**
     * Method to create MySQL connection configuration from properties.
     * @param properties the MySQL connection properties
     * @return the MySQL connection configuration
     */
    public MySqlConnectionConfiguration getConnectionConfig(MysqlConnectionProperties properties) {
        return MySqlConnectionConfiguration.builder()
                .host(properties.host())
                .port(properties.port())
                .database(properties.database())
                .username(properties.username())
                .password(properties.password())
                .build();
    }

    /**
     * Bean definition for the MySQL connection pool.
     * @param properties the MySQL connection properties
     * @return the configured ConnectionPool
     */
    @Bean
    public ConnectionPool connectionPool(MysqlConnectionProperties properties) {
        // Builder de configuración del driver R2DBC para MySQL
        MySqlConnectionConfiguration configuration = getConnectionConfig(properties);

        // ConnectionFactory propio del driver MySQL R2DBC
        ConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);

        // Configuración del pool (io.r2dbc.pool)
        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .name("api-mysql-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

        return new ConnectionPool(poolConfiguration);
    }
}