package com.powerup.api.handler;

import com.powerup.api.mapper.UserAuthMapper;
import com.powerup.model.userauth.UserAuth;
import com.powerup.usecase.userauth.UserAuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.powerup.api.util.LogMessages.SAVE_USER_DATA;
import static com.powerup.api.util.LogMessages.SAVE_USER_START;

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
        return request.bodyToMono(UserAuth.class)
                .flatMap(user -> {
                    log.info(SAVE_USER_DATA, request.path(), user);
                    return userAuthUseCase.saveUser(user);
                })
                .then(ServerResponse.ok().build());
    }
}
