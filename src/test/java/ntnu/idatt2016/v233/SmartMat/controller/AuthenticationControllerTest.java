package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class AuthenticationControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;


    @Test
    public void token_validCredentials_shouldReturnToken() {
        LoginRequest loginRequest = new LoginRequest("kari123", "sjokoladekake");

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(Authority.USER.toString()));
            }

            @Override
            public Object getCredentials() {
                return "test";
            }

            @Override
            public Object getDetails() {
                return "test";
            }

            @Override
            public Object getPrincipal() {
                return "test";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test";
            }
        });



        RestTemplateBuilder rb = new RestTemplateBuilder();
        rb.setConnectTimeout(Duration.ofSeconds(10));
        rb.setReadTimeout(Duration.ofSeconds(10));
        rb.requestFactory(() -> new HttpComponentsClientHttpRequestFactory());
        rb.errorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return statusCode.is5xxServerError();
            }
        });


        RestTemplate restTemplate = rb.build();


        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/auth/credentials", loginRequest, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

}
