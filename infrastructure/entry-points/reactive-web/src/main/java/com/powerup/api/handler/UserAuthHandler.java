package com.powerup.api;

import com.powerup.api.mapper.UserAuthMapper;
import com.powerup.api.util.UserAuthLogMessages;
import com.powerup.model.userauth.UserAuth;
import com.powerup.usecase.userauth.UserAuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthHandler {

    private final UserAuthUseCase userAuthUseCase;
    private final UserAuthMapper userAuthMapper;

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        log.info(UserAuthLogMessages.SAVE_USER_START, request.path());
        return request.bodyToMono(UserAuth.class)
                .flatMap(user -> {
                    log.info(UserAuthLogMessages.SAVE_USER_DATA, request.path(), user);
                    return userAuthUseCase.saveUser(user);
                })
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        log.info(UserAuthLogMessages.GET_ALL_USERS, request.path());
        return ServerResponse.ok().body(userAuthUseCase.getAllUsers(), UserAuth.class);
    }

    public Mono<ServerResponse> getUserByIdNumber(ServerRequest request) {
        String idNumber = request.pathVariable("idNumber");
        log.info(UserAuthLogMessages.GET_USER_BY_ID, request.path(), idNumber);
        return userAuthUseCase.getUserByIdNumber(idNumber)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUserByIdNumber(ServerRequest request) {
        String idNumber = request.pathVariable("idNumber");
        log.info(UserAuthLogMessages.DELETE_USER_BY_ID, request.path(), idNumber);
        return userAuthUseCase.deleteUserByIdNumber(idNumber)
                .then(ServerResponse.noContent().build());
    }
}
