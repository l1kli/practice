package todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import todo.AuthService;
import todo.JwtService;
import todo.dto.RegisterRequest;
import todo.entity.User;
import todo.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Test
    void registerSuccess() {

        UserRepository userRepository =
                mock(UserRepository.class);

        PasswordEncoder encoder =
                mock(PasswordEncoder.class);

        JwtService jwtService =
                mock(JwtService.class);

        when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        when(encoder.encode(any()))
                .thenReturn("encoded");

        when(jwtService.generateToken(any()))
                .thenReturn("token");

        AuthService service =
                new AuthService(
                        userRepository,
                        encoder,
                        jwtService
                );

        RegisterRequest request =
                new RegisterRequest(
                        "test@mail.com",
                        "1234",
                        "1234"
                );

        assertNotNull(service.register(request));
    }
    @Test
    void registerPasswordsDoNotMatch() {

        UserRepository userRepository =
                mock(UserRepository.class);

        PasswordEncoder encoder =
                mock(PasswordEncoder.class);

        JwtService jwtService =
                mock(JwtService.class);

        AuthService service =
                new AuthService(
                        userRepository,
                        encoder,
                        jwtService
                );

        RegisterRequest request =
                new RegisterRequest(
                        "test@mail.com",
                        "1234",
                        "1111"
                );

        assertThrows(
                RuntimeException.class,
                () -> service.register(request)
        );
    }
    @Test
    void registerShortPassword() {

        UserRepository userRepository =
                mock(UserRepository.class);

        PasswordEncoder encoder =
                mock(PasswordEncoder.class);

        JwtService jwtService =
                mock(JwtService.class);

        when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        AuthService service =
                new AuthService(
                        userRepository,
                        encoder,
                        jwtService
                );

        RegisterRequest request =
                new RegisterRequest(
                        "test@mail.com",
                        "123",
                        "123"
                );

        assertThrows(
                RuntimeException.class,
                () -> service.register(request)
        );
    }
}