package com.powerup.usecase.userauth;

import com.powerup.model.roleauth.RoleAuth;
import com.powerup.model.roleauth.gateways.RoleAuthRepository;
import com.powerup.model.userauth.AuthResponse;
import com.powerup.model.userauth.gateways.JwtProvider;
import com.powerup.model.userauth.gateways.PasswordEncoder;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.usecase.exceptions.InvalidCredentialsException;
import reactor.core.publisher.Mono;

/**
 * Use case for authenticating a user.
 * It verifies the user's credentials and generates a JWT token upon successful authentication.
 * @version 1.0
 * @since 2025-08-23
 */
public class AuthenticationUseCase {

    private final UserAuthRepository userRepository;
    private final RoleAuthRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final String ERROR_INVALID_CREDENTIALS = "User or password invalid";

    /**
     * Constructor for AuthenticationUseCase.
     * @param userRepository Repository for user authentication data.
     * @param roleRepository Repository for role authentication data.
     * @param passwordEncoder Service for encoding and verifying passwords.
     * @param jwtProvider Service for generating JWT tokens.
     */
    public AuthenticationUseCase(UserAuthRepository userRepository, RoleAuthRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Authenticate a user with the given username and password.
     * @param username The user's username (email).
     * @param password The user's password.
     * @return A Mono emitting an AuthResponse containing the JWT token if authentication is successful,
     *         or an error if authentication fails.
     */
    public Mono<AuthResponse> authenticate(String username, String password) {
        return userRepository.getByEmail(username)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException(ERROR_INVALID_CREDENTIALS)))
                .flatMap(user -> {
                    if (!user.isEnabled() || !passwordEncoder.matches(password, user.getPasswordHash())) {
                        return Mono.error(new InvalidCredentialsException(ERROR_INVALID_CREDENTIALS));
                    }
                    Mono<RoleAuth> role = roleRepository.findByIdRole(user.getIdRole());
                    return role.flatMap(roleAuth ->
                            jwtProvider.generateToken(user.getUserId()+"", roleAuth.getName())
                    ).map(token -> new AuthResponse(token, "Bearer"));
                });
    }

}
