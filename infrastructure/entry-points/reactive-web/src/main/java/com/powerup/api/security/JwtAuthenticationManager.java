package com.powerup.api.security;

import com.powerup.model.userauth.gateways.JwtProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

/**
 * JwtAuthenticationManager is responsible for authenticating users based on JWT tokens.
 * It implements the ReactiveAuthenticationManager interface to provide reactive authentication capabilities.
 * The class uses a JwtProvider to validate tokens and extract user information and roles.
 *
 * @version 1.0
 * @since 2025-08-23
 */
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationManager(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Authenticates a user based on the provided JWT token.
     *
     * @param authentication The authentication request object containing the JWT token.
     * @return A Mono emitting the authenticated Authentication object if the token is valid,
     *         or an empty Mono if the token is invalid.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        return jwtProvider.validate(token)
                .flatMap(valid -> {
                    if (!valid) return Mono.empty();
                    return jwtProvider.getUsername(token)
                            .zipWith(jwtProvider.getRoles(token))
                            .map(tuple -> {
                                var username = tuple.getT1();
                                var roles = tuple.getT2();
                                var authorities = roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .toList();
                                return new UsernamePasswordAuthenticationToken(username, null, authorities);
                            });
                });
    }

}
