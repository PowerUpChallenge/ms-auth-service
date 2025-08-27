package com.powerup.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@RequiredArgsConstructor
public class UserAuthRouterRest {

    private final String USER_PATH = "/api/v1/usuarios";

    private final UserAuthHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET(USER_PATH), userHandler::getUserByIdNumber)
                .andRoute(POST(USER_PATH), userHandler::saveUser)
                .and(route(GET(USER_PATH), userHandler::getAllUsers));
    }
}
