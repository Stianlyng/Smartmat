package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.request.LoginRequest;
import ntnu.idatt2016.v233.SmartMat.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authenticationController = new AuthenticationController(tokenService, authenticationManager);
    }

    @Test
    void testToken_success() {
        String expectedToken = "generated_token";
        LoginRequest validLoginRequest = new LoginRequest("valid_user", "valid_password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                validLoginRequest.username(),
                validLoginRequest.password()
        ))).thenReturn(authentication);

        when(tokenService.generateToken(authentication)).thenReturn(expectedToken);

        ResponseEntity<?> response = authenticationController.token(validLoginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedToken, response.getBody());
    }

    @Test
    void testToken_invalidInput() {
        LoginRequest invalidLoginRequest = new LoginRequest("invalid_username", "");

        ResponseEntity<?> response = authenticationController.token(invalidLoginRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Invalid input.", response.getBody());
    }

    @Test
    void testToken_invalidCredentials() {
        LoginRequest invalidCredentialsRequest = new LoginRequest("invalid_user", "invalid_password");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                invalidCredentialsRequest.username(),
                invalidCredentialsRequest.password()
        ))).thenThrow(new BadCredentialsException("Invalid username or password."));

        ResponseEntity<?> response = authenticationController.token(invalidCredentialsRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Error: Invalid username or password.", response.getBody());
    }

    @Test
    void testToken_tokenGenerationFailure() {
        LoginRequest validLoginRequest = new LoginRequest("valid_user", "valid_password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                validLoginRequest.username(),
                validLoginRequest.password()
        ))).thenReturn(authentication);

        when(tokenService.generateToken(authentication)).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = authenticationController.token(validLoginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: Unable to generate token. Please try again later.", response.getBody());
    }

}
