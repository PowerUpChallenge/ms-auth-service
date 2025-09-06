package com.powerup.api.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JwtSecurityContextRepository is responsible for loading the SecurityContext from the JWT token present in the
 * Authorization header of the HTTP request.
 * It implements the ServerSecurityContextRepository interface to integrate with Spring Security's reactive support.
 * It uses a JwtAuthenticationManager to authenticate the token and create a SecurityContext.
 *
 * @version 1.0
 * @since 2025-08-23
 */
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager authManager;

    public JwtSecurityContextRepository(JwtAuthenticationManager authManager) {
        this.authManager = authManager;
    }

    /**
     * Saving the SecurityContext is not supported in this implementation.
     *
     * @param exchange The current server exchange.
     * @param context  The security context to save.
     * @return A Mono that completes when the operation is done.
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    /**
     * Loads the SecurityContext from the JWT token in the Authorization header.
     *
     * @param exchange The current server exchange.
     * @return A Mono emitting the SecurityContext if the token is valid, or an empty Mono if not.
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
            return authManager.authenticate(auth).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
