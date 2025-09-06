package com.powerup.usecase.roleauth;

import com.powerup.model.roleauth.RoleAuth;
import com.powerup.model.roleauth.gateways.RoleAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleAuthUseCaseTest {

    @Mock
    private RoleAuthRepository roleAuthRepository;

    @InjectMocks
    private RoleAuthUseCase roleAuthUseCase;

    private RoleAuth role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new RoleAuth();
        role.setIdRole(1L);
        role.setName("ADMIN");
    }

    @Test
    void saveRole_shouldCompleteSuccessfully() {
        when(roleAuthRepository.saveRole(any(RoleAuth.class))).thenReturn(Mono.empty());

        StepVerifier.create(roleAuthUseCase.saveRole(role))
                .verifyComplete();
    }

    @Test
    void saveRole_shouldPropagateError() {
        when(roleAuthRepository.saveRole(any(RoleAuth.class)))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(roleAuthUseCase.saveRole(role))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                                         e.getMessage().equals("DB error"))
                .verify();
    }

    @Test
    void getByIdRole_shouldReturnRole_whenExists() {
        when(roleAuthRepository.findByIdRole(1L)).thenReturn(Mono.just(role));

        StepVerifier.create(roleAuthUseCase.getByIdRole(1L))
                .expectNextMatches(found -> found.getIdRole().equals(1L) && found.getName().equals("ADMIN"))
                .verifyComplete();
    }

    @Test
    void getByIdRole_shouldReturnEmpty_whenNotExists() {
        when(roleAuthRepository.findByIdRole(2L)).thenReturn(Mono.empty());

        StepVerifier.create(roleAuthUseCase.getByIdRole(2L))
                .verifyComplete();
    }
}
