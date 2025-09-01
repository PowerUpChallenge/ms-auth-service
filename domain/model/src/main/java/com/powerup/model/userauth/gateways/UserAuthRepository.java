package com.powerup.model.userauth.gateways;

import com.powerup.model.userauth.UserAuth;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing UserAuth entities.
 * Provides methods for saving, retrieving, updating, and deleting users.
 * Uses reactive types Mono and Flux for asynchronous operations.
 * @version 1.0
 * @since 2025-08-23
 */
public interface UserAuthRepository {

    /**
     * Save a new user.
     *
     * @param user The user to be saved.
     * @return A Mono signaling completion.
     */
    Mono<Void> saveUser(UserAuth user);

    /**
     * Retrieve a user by their ID number.
     *
     * @param idNumber The ID number of the user.
     * @return A Mono emitting the user if found, or empty if not found.
     */
    Mono<UserAuth> getByIdNumber(String idNumber);

    /**
     * Retrieve a user by their email.
     *
     * @param email The email of the user.
     * @return A Mono emitting the user if found, or empty if not found.
     */
    Mono<UserAuth> getByEmail(String email);

    /**
     * Update an existing user.
     *
     * @param user The user with updated information.
     * @return A Mono emitting the updated user.
     */
    Mono<UserAuth> updateUser(UserAuth user);

    /**
     * Retrieve all users.
     *
     * @return A Flux emitting all users.
     */
    Flux<UserAuth> getAll();

    /**
     * Delete a user by their ID number.
     *
     * @param idNumber The ID number of the user to be deleted.
     * @return A Mono signaling completion.
     */
    Mono<Void> deleteByIdNumber(String idNumber);

}
