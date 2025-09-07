package com.powerup.api.router;

import com.powerup.api.config.RoutesProperties;
import com.powerup.api.dto.request.UserAuthRequestDTO;
import com.powerup.api.dto.request.UserValidateRequestDTO;
import com.powerup.api.dto.response.UserAuthResponseDTO;
import com.powerup.api.dto.response.UserValidateResponseDTO;
import com.powerup.api.handler.UserAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
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
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveUser",
                            summary = "Save a new user",
                            description = "Saves a new user to the system with authentication data",
                            tags = {"User Management"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "User data for registration",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserAuthRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "User created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = UserAuthResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid user data - validation failed"
                                    ),
                                    @ApiResponse(
                                            responseCode = "409",
                                            description = "User already exists"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/find",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "getUserByIdNumber",
                            summary = "Find user by identification number",
                            description = "Retrieves user information by their identification number",
                            tags = {"User Query"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Identification number to search",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserValidateRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "User found successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = UserValidateResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid request data"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "User not found"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .add(route(POST(routesProperties.getUserApiPath()), userHandler::saveUser))
                .add(route(POST(routesProperties.getUserApiPath() + "/find"), userHandler::getUserByIdNumber))
                .build();
    }

}
