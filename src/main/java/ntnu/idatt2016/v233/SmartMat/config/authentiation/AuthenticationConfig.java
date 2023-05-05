package ntnu.idatt2016.v233.SmartMat.config.authentiation;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import ntnu.idatt2016.v233.SmartMat.util.rsa.Jwks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * Configures the authentication for the application.
 * @author Anders and  birk
 * @version 1.1
 */
@Configuration
public class AuthenticationConfig {

    private RSAKey rsaKey;

    /**
     * Configures a JwtDecoder for decoding JWT tokens.
     *
     * @return A JwtDecoder instance.
     * @throws JOSEException If an error occurs during decoding.
     */
    @Bean
    public JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    /**
     * Configures a JwtEncoder for encoding JWT tokens.
     *
     * @return A JwtEncoder instance.
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Configures the authentication manager for the application.
     * @param userDetailsService the user details service
     * @param passwordEncoder the password encoder
     * @return the authentication manager
     */
    @Bean
    AuthenticationManager authenticationManager (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider autprovider = new DaoAuthenticationProvider();
        autprovider.setUserDetailsService(userDetailsService);
        autprovider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(autprovider);
    }


    /**
     * Configures the JWKSource instance for handling RSA keys.
     *
     * @return A JWKSource instance.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    /**
     * Configures the password encoder.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
