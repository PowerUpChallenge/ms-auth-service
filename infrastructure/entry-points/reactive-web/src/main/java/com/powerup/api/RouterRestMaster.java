package com.powerup.api;

import com.powerup.api.router.AuthenticationRouterRest;
import com.powerup.api.router.UserAuthRouterRest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterRestMaster {

    private final UserAuthRouterRest userAuthRouterRest;
    private final AuthenticationRouterRest authenticationRouterRest;

    @Bean(name = "masterRouterFunction")
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(userAuthRouterRest.routerFunction())
                .add(authenticationRouterRest.routerFunction())
                .build();
    }

}
