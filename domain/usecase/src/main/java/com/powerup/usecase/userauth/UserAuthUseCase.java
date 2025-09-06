package com.powerup.usecase.userauth;

import com.powerup.model.userauth.UserAuth;
import com.powerup.usecase.exceptions.EmailAlreadyExistException;
import com.powerup.usecase.exceptions.UserAuthNotFoundException;
import com.powerup.usecase.exceptions.UserAuthValidationException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Use case class for managing UserAuth entities.
 * Provides methods for saving, retrieving, updating, and deleting users.
 * Uses reactive types Mono and Flux for asynchronous operations.
 *
 * @version 1.0
 * @since 2025-08-23
 */
public class UserAuthUseCase {

    private final com.powerup.model.userauth.gateways.UserAuthRepository userAuthRepository;
    private final com.powerup.model.userauth.gateways.PasswordEncoder passwordEncoder;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final String ERROR_NAME_REQUIRED = "Name is required";
    private static final String ERROR_LASTNAME_REQUIRED = "Lastname is required";
    private static final String ERROR_EMAIL_REQUIRED = "Email is required";
    private static final String ERROR_EMAIL_INVALID = "Invalid email format";
    private static final String ERROR_EMAIL_DUPLICATE = "Email is already registered";
    private static final String ERROR_SALARY_REQUIRED = "Base salary is required";
    private static final String ERROR_SALARY_RANGE = "Base salary must be between 0 and 15000000";
    private static final String MAX_SALARY_RANGE = "15000000";


    public UserAuthUseCase(com.powerup.model.userauth.gateways.UserAuthRepository userAuthRepository, com.powerup.model.userauth.gateways.PasswordEncoder passwordEncoder) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieve a user by their email.
     *
     * @param email The email of the user.
     * @return A Mono emitting the user if found, or empty if not found.
     */
    public Mono<UserAuth> getUserByEmail(String email) {
        return userAuthRepository.getByEmail(email)
                .switchIfEmpty(Mono.error(new UserAuthNotFoundException(email)));
    }

    /**
     * Save a new user.
     *
     * @param user The user to be saved.
     * @return A Mono signaling completion.
     */
    public Mono<Void> saveUser(UserAuth user) {

        List<String> errors = new ArrayList<>();
        /**Custom UserAuth Validations*/
        addError(errors, validateName(user.getName()));
        addError(errors, validateLastname(user.getLastname()));
        addError(errors, validateEmail(user.getEmail()));
        addError(errors, validateBaseSalary(user.getBaseSalary()));

        if (!errors.isEmpty()) {
            return Mono.error(new UserAuthValidationException(String.join(", ", errors)));
        }

        return userAuthRepository.getByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.error(new EmailAlreadyExistException(ERROR_EMAIL_DUPLICATE)))
                .switchIfEmpty(Mono.defer(() -> {
                    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
                    user.setEnabled(true);
                    return userAuthRepository.saveUser(user);
                }))
                .then();
    }

    private void addError(List<String> errors, String error) {
        if (error != null) errors.add(error);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ERROR_NAME_REQUIRED;
        }
        return null;
    }

    private String validateLastname(String lastname) {
        if (lastname == null || lastname.trim().isEmpty()) {
            return ERROR_LASTNAME_REQUIRED;
        }
        return null;
    }

    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ERROR_EMAIL_REQUIRED;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ERROR_EMAIL_INVALID;
        }
        return null;
    }

    private String validateBaseSalary(java.math.BigDecimal baseSalary) {
        if (baseSalary == null) {
            return ERROR_SALARY_REQUIRED;
        }
        if (baseSalary.compareTo(java.math.BigDecimal.ZERO) < 0 ||
            baseSalary.compareTo(new java.math.BigDecimal(MAX_SALARY_RANGE)) > 0) {
            return ERROR_SALARY_RANGE;
        }
        return null;
    }
}