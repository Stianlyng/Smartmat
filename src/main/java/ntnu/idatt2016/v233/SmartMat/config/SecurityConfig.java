package ntnu.idatt2016.v233.SmartMat.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configs for security and authentication
 * @author Birk
 * @version 1.0
 */
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    /**
     * Configures the HttpSecurity for the application.
     * Dose not need ot have csrf enabled, because we are using jwt
     * and the application is stateless
     *
     * @param http HttpSecurity to configure
     * @return SecurityFilterChain with configured HttpSecurity
     * @throws Exception if an error occurs
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.POST, "api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/groups/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
