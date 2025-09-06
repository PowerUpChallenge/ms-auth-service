package com.powerup.api.handler;

import com.powerup.api.dto.request.AuthRequestDTO;
import com.powerup.api.mapper.AuthResponseMapper;
import com.powerup.usecase.userauth.AuthenticationUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.powerup.api.util.LogMessages.*;

/**
 * AuthenticationHandler is responsible for handling authentication-related HTTP requests.
 * It uses the AuthenticationUseCase to perform the actual authentication logic.
 * The handler processes login requests, validates user credentials, and returns appropriate responses.
 *
 * @version 1.0
 * @since 2025-08-23
 */
@Component
@Slf4j
public class AuthenticationHandler {

    private final AuthenticationUseCase authUseCase;
    private final AuthResponseMapper authResponseMapper;

    public AuthenticationHandler(AuthenticationUseCase authUseCase, AuthResponseMapper authResponseMapper) {
        this.authUseCase = authUseCase;
        this.authResponseMapper = authResponseMapper;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info(SAVE_USER_START, request.path());
        return request.bodyToMono(AuthRequestDTO.class)
                .flatMap(authRequestDTO -> authUseCase.authenticate(authRequestDTO.getUsername(), authRequestDTO.getPassword()))
                .map(authResponseMapper::toResponseDTO)
                .flatMap(responseDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseDTO))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
