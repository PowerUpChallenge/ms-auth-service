package com.powerup.usecase.userauth;

import com.powerup.model.userauth.UserAuth;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.usecase.exceptions.EmailAlreadyExistException;
import com.powerup.usecase.exceptions.UserAuthNotFoundException;
import com.powerup.usecase.exceptions.UserAuthValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class UserAuthUseCaseTest {

    @Mock
    private UserAuthRepository userAuthRepository;

    @InjectMocks
    private UserAuthUseCase userAuthUseCase;

    private UserAuth validUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validUser = new UserAuth();
        validUser.setIdNumber("123");
        validUser.setIdType(1);
        validUser.setName("John");
        validUser.setLastname("Doe");
        validUser.setBirthDate(LocalDate.of(1990, 5, 20));
        validUser.setAddress("Street 123");
        validUser.setPhone("5551234");
        validUser.setEmail("john.doe@test.com");
        validUser.setBaseSalary(new BigDecimal("10000"));
        validUser.setPasswordHash("hashed");
        validUser.setIdRole(1L);
        validUser.setEnabled(true);
    }

    @Test
    void getUserByEmail_shouldReturnUser_whenExists() {
        when(userAuthRepository.getByEmail("john.doe@test.com")).thenReturn(Mono.just(validUser));

        StepVerifier.create(userAuthUseCase.getUserByEmail("john.doe@test.com"))
                .expectNext(validUser)
                .verifyComplete();

        verify(userAuthRepository).getByEmail("john.doe@test.com");
    }

    @Test
    void getUserByEmail_shouldThrowException_whenNotFound() {
        when(userAuthRepository.getByEmail("notfound@test.com")).thenReturn(Mono.empty());

        StepVerifier.create(userAuthUseCase.getUserByEmail("notfound@test.com"))
                .expectErrorMatches(throwable -> throwable instanceof UserAuthNotFoundException &&
                                                 throwable.getMessage().contains("notfound@test.com"))
                .verify();
    }

    @Test
    void saveUser_shouldSave_whenValidAndNotExists() {
        when(userAuthRepository.getByEmail(validUser.getEmail())).thenReturn(Mono.empty());
        when(userAuthRepository.saveUser(validUser)).thenReturn(Mono.just(validUser).then());

        StepVerifier.create(userAuthUseCase.saveUser(validUser))
                .verifyComplete();

        verify(userAuthRepository).getByEmail(validUser.getEmail());
        verify(userAuthRepository).saveUser(validUser);
    }

    @Test
    void saveUser_shouldThrowValidationException_whenInvalidData() {
        UserAuth invalidUser = new UserAuth();
        invalidUser.setName(""); // invalid name
        invalidUser.setLastname(""); // invalid lastname
        invalidUser.setEmail("bad-email"); // invalid email
        invalidUser.setBaseSalary(new BigDecimal("-10")); // invalid salary

        StepVerifier.create(userAuthUseCase.saveUser(invalidUser))
                .expectError(UserAuthValidationException.class)
                .verify();

        verify(userAuthRepository, never()).saveUser(any());
    }

    @Test
    void saveUser_shouldThrowEmailAlreadyExistException_whenEmailAlreadyExists() {

        when(userAuthRepository.getByEmail(anyString()))
                .thenReturn(Mono.just(validUser));

        when(userAuthRepository.saveUser(any(UserAuth.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(userAuthUseCase.saveUser(validUser))
                .expectError(EmailAlreadyExistException.class)
                .verify();

        verify(userAuthRepository).getByEmail(validUser.getEmail());
    }
}
