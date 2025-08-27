package com.powerup.r2dbc;

import com.powerup.r2dbc.entity.UserAuthEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive repository interface for UserAuthEntity.
 * Extends ReactiveCrudRepository for basic CRUD operations
 * and ReactiveQueryByExampleExecutor for query by example functionality.
 * @version 1.0
 * @since 2025-08-23
 */
public interface UserAuthReactiveRepository extends ReactiveCrudRepository<UserAuthEntity, Long>, ReactiveQueryByExampleExecutor<UserAuthEntity> {

    /**
     * Finds a UserAuthEntity by its email.
     * @param email the email of the user
     * @return a Mono emitting the found UserAuthEntity or empty if not found
     */
    Mono<UserAuthEntity> findByEmail(String email);

}
