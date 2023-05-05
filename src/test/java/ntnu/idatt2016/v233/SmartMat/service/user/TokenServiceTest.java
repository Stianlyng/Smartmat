package ntnu.idatt2016.v233.SmartMat.service.user;

import ntnu.idatt2016.v233.SmartMat.service.user.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenServiceTest {

    private TokenService tokenService;
    private JwtEncoder jwtEncoder;

    @BeforeEach
    void setUp() {
        jwtEncoder = mock(JwtEncoder.class);
        tokenService = new TokenService(jwtEncoder);
    }

    @Test
    void testGenerateToken() {
        // Given
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);

        Instant now = Instant.now();
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "none")
                .claim("iss", "self")
                .claim("issuedAt", now)
                .claim("expiresAt", now)
                .claim("sub", "user")
                .claim("scope", "ROLE_ADMIN ROLE_USER")
                .build();

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // When
        String token = tokenService.generateToken(authentication);

        // Then
        assertNotNull(token);
        assertEquals("test-token", token);
    }
}
