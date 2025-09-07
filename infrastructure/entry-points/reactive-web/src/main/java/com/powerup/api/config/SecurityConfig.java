package com.powerup.api.config;

import com.powerup.api.security.JwtAuthenticationManager;
import com.powerup.api.security.JwtSecurityContextRepository;
import com.powerup.model.userauth.gateways.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_ASESOR = "ASESOR";
    private static final String ROLE_USUARIO = "USUARIO";

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(jwtProvider);
    }

    @Bean
    public JwtSecurityContextRepository jwtSecurityContextRepository(JwtAuthenticationManager manager) {
        return new JwtSecurityContextRepository(manager);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         JwtSecurityContextRepository securityContextRepository,
                                                         JwtAuthenticationManager authManager) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/api/v1/auth/login").permitAll()
                        .pathMatchers("/api/v1/usuarios/find").permitAll()
                        .pathMatchers("/actuator").permitAll()
                        .pathMatchers("/swagger-ui/**").permitAll()
                        .pathMatchers("/v3/api-docs/**").permitAll()
                        .pathMatchers("/webjars/**").permitAll()
                        .pathMatchers("/api/v1/usuarios").hasAnyRole(ROLE_ADMIN, ROLE_ASESOR)
                        .anyExchange().authenticated()
                )
                .authenticationManager(authManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }
}
