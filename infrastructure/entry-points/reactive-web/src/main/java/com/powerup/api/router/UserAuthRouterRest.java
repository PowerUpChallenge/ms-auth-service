package com.powerup.api.router;

import com.powerup.api.config.RoutesProperties;
import com.powerup.api.dto.request.UserAuthRequestDTO;
import com.powerup.api.handler.UserAuthHandler;
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

@Component
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for users handling")
public class UserAuthRouterRest {

    private final UserAuthHandler userHandler;
    private final RoutesProperties routesProperties;

    @Bean(name = "userAuthRouterFunction")
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    operation = @Operation(
                            operationId = "saveUser",
                            summary = "Save a new user",
                            description = "Saves a new user to the system.",
                            requestBody = @RequestBody(
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAuthRequestDTO.class)
                            )
                            ),
                            responses = {
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "200",
                                            description = "User saved successfully"
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid user data"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST(routesProperties.getUserApiPath()), userHandler::saveUser);
    }

}
