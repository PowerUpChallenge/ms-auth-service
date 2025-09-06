package com.powerup.api.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global error handler for handling exceptions in a reactive web application.
 * Implements the ErrorWebExceptionHandler interface to provide custom error responses.
 *
 * @version 1.0
 * @since 2025-08-31
 */
@Component
@Order(-2)
@Slf4j
public class GlobalErrorHandler  implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final ExceptionMapper exceptionMapper;

    public GlobalErrorHandler(ObjectMapper objectMapper, ExceptionMapper exceptionMapper) {
        this.objectMapper = objectMapper;
        this.exceptionMapper = exceptionMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ExceptionMapper.ErrorDefinition definition = exceptionMapper.map(ex);
        response.setStatusCode(definition.status());
        log.error(ex.getMessage());
        ErrorCustomResponse errorCustomResponse = new ErrorCustomResponse(definition.message(), definition.status().value());

        return writeResponse(response, errorCustomResponse);
    }

    private Mono<Void> writeResponse(ServerHttpResponse response, ErrorCustomResponse errorCustomResponse) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorCustomResponse);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }
}
