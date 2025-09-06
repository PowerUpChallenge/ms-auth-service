package com.powerup.model.roleauth.gateways;

import com.powerup.model.roleauth.RoleAuth;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing RoleAuth entities.
 * Provides methods for saving and retrieving roles.
 * Uses reactive type Mono for asynchronous operations.
 * @version 1.0
 * @since 2025-08-23
 */
public interface RoleAuthRepository {

    /**
     * Save a new role.
     * @param role
     * @return
     */
    Mono<Void> saveRole(RoleAuth role);

    /**
     * Find a role by its ID.
     * @param idRole The ID of the role to find.
     * @return A Mono emitting the RoleAuth if found, or empty if not found.
     */
    Mono<RoleAuth> findByIdRole(Long idRole);

}
