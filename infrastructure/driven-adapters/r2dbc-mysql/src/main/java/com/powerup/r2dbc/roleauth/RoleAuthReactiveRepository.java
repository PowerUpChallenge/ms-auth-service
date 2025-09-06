package com.powerup.r2dbc.roleauth;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive repository interface for RoleAuthEntity.
 * Extends ReactiveCrudRepository for basic CRUD operations
 * and ReactiveQueryByExampleExecutor for query by example functionality.
 * @version 1.0
 * @since 2025-08-23
 */
public interface RoleAuthReactiveRepository extends ReactiveCrudRepository<RoleAuthEntity, Long>, ReactiveQueryByExampleExecutor<RoleAuthEntity> {


}
