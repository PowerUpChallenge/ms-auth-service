package com.powerup.api.router;

import com.powerup.api.config.RoutesProperties;
import com.powerup.api.dto.request.AuthRequestDTO;
import com.powerup.api.handler.AuthenticationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Router class for handling authentication-related endpoints.
 * Defines routes for user login and token generation.
 *
 * @version 1.0
 * @since 2025-08-27 *
 */
@Component
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and token generation")
public class AuthenticationRouterRest {

    private final AuthenticationHandler handler;
    private final RoutesProperties routesProperties;

    @Bean(name = "authenticationRouterFunction")
    @RouterOperations({
            @RouterOperation(
                    path = "/auth/login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Authenticate user",
                            description = "Validates user credentials and generates a JWT token.",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AuthRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "200",
                                            description = "Successful authentication"
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "401",
                                            description = "Invalid credentials"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST(routesProperties.getAuthApiPath()), handler::login);
    }
}
