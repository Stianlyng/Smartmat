package ntnu.idatt2016.v233.SmartMat.util.rsa;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwksTest is a test class for the Jwks utility class.
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
class JwksTest {

    @Test
    public void testGenerateRsa() {
        RSAKey rsaKey = Jwks.generateRsa();

        JWKSet jwkSet = new JWKSet(rsaKey);


        assertEquals(1, jwkSet.getKeys().size());
        assertNotNull(jwkSet.toString());
        assertNotNull(jwkSet.toString(true));
    }
}