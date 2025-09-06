package com.powerup.r2dbc;
import com.powerup.model.userauth.UserAuth;
import com.powerup.r2dbc.userauth.UserAuthEntity;
import com.powerup.r2dbc.userauth.UserAuthReactiveRepository;
import com.powerup.r2dbc.userauth.UserAuthReactiveRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserAuthReactiveRepositoryAdapterTest {

    @Mock
    private UserAuthReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    @InjectMocks
    private UserAuthReactiveRepositoryAdapter adapter;

    private UserAuth userDomain;
    private UserAuthEntity userEntity;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        userDomain = new UserAuth(
                "12345",
                1,
                "Luis",
                "Velez",
                LocalDate.of(1990, 1, 1),
                "Street 123",
                "555-1234",
                "luis@test.com",
                new BigDecimal("1000000"),
                "hash123",
                1L,
                true
        );

        userEntity = new UserAuthEntity();
        userEntity.setIdNumber("12345");
        userEntity.setName("Luis");

        // mapeo dominio → entidad
        when(mapper.map(userDomain, UserAuthEntity.class)).thenReturn(userEntity);
        // mapeo entidad → dominio
        when(mapper.map(userEntity, UserAuth.class)).thenReturn(userDomain);

        // mock de transactionalOperator
        when(transactionalOperator.transactional((Flux<Object>) any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void saveUser_shouldSaveAndApplyTransaction() {
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));

        StepVerifier.create(adapter.saveUser(userDomain))
                .verifyComplete();

        verify(repository).save(userEntity);
        verify(transactionalOperator).transactional((Mono<Object>) any());
    }

    @Test
    void getByEmail_shouldReturnMappedUser() {
        when(repository.findByEmail("luis@test.com")).thenReturn(Mono.just(userEntity));

        StepVerifier.create(adapter.getByEmail("luis@test.com"))
                .expectNext(userDomain)
                .verifyComplete();

        verify(repository).findByEmail("luis@test.com");
        verify(mapper).map(userEntity, UserAuth.class);
    }

    @Test
    void saveUser_shouldPropagateError() {
        when(repository.save(userEntity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(adapter.saveUser(userDomain))
                .expectErrorMatches(e -> e instanceof RuntimeException && e.getMessage().equals("DB error"))
                .verify();

        verify(repository).save(userEntity);
    }
}