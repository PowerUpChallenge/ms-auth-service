package com.powerup.api.handler;

import com.powerup.api.dto.request.UserAuthRequestDTO;
import com.powerup.api.mapper.UserAuthMapper;
import com.powerup.usecase.userauth.UserAuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.powerup.api.util.LogMessages.*;

/**
 * UserAuthHandler is responsible for handling user authentication-related HTTP requests.
 * It uses the UserAuthUseCase to perform the actual user management logic.
 * The handler processes requests to save, retrieve, and delete users.
 *
 * @version 1.0
 * @since 2024-06
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthHandler {

    private final UserAuthUseCase userAuthUseCase;
    private final UserAuthMapper userAuthMapper;

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        log.info(SAVE_USER_START, request.path());
        return request.bodyToMono(UserAuthRequestDTO.class)
                .doOnNext(user -> log.info(SAVE_USER_DATA, request.path(), user))
                .map(userAuthMapper::toModel)
                .flatMap(user ->
                        userAuthUseCase.saveUser(user)
                                .doOnSuccess(v -> log.info(SAVE_USER_SUCCESS, request.path()))
                                .doOnError(e -> log.error(SAVE_USER_ERROR, request.path(), e.getMessage()))
                                .then(ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(Map.of(
                                                "status", "success",
                                                "message", "User saved successfully",
                                                "user", userAuthMapper.toResponseDTO(user)
                                        ))
                                )
                );
    }
}
