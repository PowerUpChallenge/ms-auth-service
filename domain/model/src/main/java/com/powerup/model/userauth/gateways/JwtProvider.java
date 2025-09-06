package com.powerup.model.userauth.gateways;

import reactor.core.publisher.Mono;

import java.util.Set;

public interface JwtProvider {

    Mono<String> generateToken(String subject, String role);
    Mono<Boolean> validate(String token);
    Mono<String> getUsername(String token);
    Mono<Set<String>> getRoles(String token);

}
