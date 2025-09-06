package com.powerup.usecase.userauth;

import com.powerup.model.roleauth.RoleAuth;
import com.powerup.model.roleauth.gateways.RoleAuthRepository;
import com.powerup.model.userauth.UserAuth;
import com.powerup.model.userauth.gateways.JwtProvider;
import com.powerup.model.userauth.gateways.PasswordEncoder;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.usecase.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthenticationUseCaseTest {

    @Mock
    private UserAuthRepository userRepository;
    @Mock
    private RoleAuthRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    private UserAuth validUser;
    private RoleAuth validRole;

    @BeforeEach
    void setUp() {
        openMocks(this);

        validUser = new UserAuth();
        validUser.setEmail("test@mail.com");
        validUser.setPasswordHash("hashedPass");
        validUser.setEnabled(true);
        validUser.setIdRole(1L);

        validRole = new RoleAuth();
        validRole.setIdRole(1L);
        validRole.setName("ADMIN");
    }

    @Test
    void authenticate_shouldReturnAuthResponse_whenCredentialsAreValid() {
        when(userRepository.getByEmail(validUser.getEmail())).thenReturn(Mono.just(validUser));
        when(passwordEncoder.matches("password", validUser.getPasswordHash())).thenReturn(true);
        when(roleRepository.findByIdRole(validUser.getIdRole())).thenReturn(Mono.just(validRole));
        when(jwtProvider.generateToken(validUser.getEmail(), validRole.getName())).thenReturn(Mono.just("jwt-token"));

        StepVerifier.create(authenticationUseCase.authenticate(validUser.getEmail(), "password"))
                .expectNextMatches(response ->
                        response != null &&
                        response.getAccessToken().equals("jwt-token") &&
                        response.getTokenType().equals("Bearer")
                )
                .verifyComplete();
    }

    @Test
    void authenticate_shouldThrowInvalidCredentials_whenUserNotFound() {
        when(userRepository.getByEmail("notfound@mail.com")).thenReturn(Mono.empty());

        StepVerifier.create(authenticationUseCase.authenticate("notfound@mail.com", "password"))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    void authenticate_shouldThrowInvalidCredentials_whenUserDisabled() {
        validUser.setEnabled(false);
        when(userRepository.getByEmail(validUser.getEmail())).thenReturn(Mono.just(validUser));

        StepVerifier.create(authenticationUseCase.authenticate(validUser.getEmail(), "password"))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    void authenticate_shouldThrowInvalidCredentials_whenPasswordDoesNotMatch() {
        when(userRepository.getByEmail(validUser.getEmail())).thenReturn(Mono.just(validUser));
        when(passwordEncoder.matches("wrong", validUser.getPasswordHash())).thenReturn(false);

        StepVerifier.create(authenticationUseCase.authenticate(validUser.getEmail(), "wrong"))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    void authenticate_shouldError_whenRoleNotFound() {
        when(userRepository.getByEmail(validUser.getEmail())).thenReturn(Mono.just(validUser));
        when(passwordEncoder.matches("password", validUser.getPasswordHash())).thenReturn(true);
        when(roleRepository.findByIdRole(validUser.getIdRole())).thenReturn(Mono.empty());

        StepVerifier.create(authenticationUseCase.authenticate(validUser.getEmail(), "passwordasd"))
                .expectError(InvalidCredentialsException.class) // <- opción: puedes lanzar esta si ajustas el código
                .verify();
    }
}
