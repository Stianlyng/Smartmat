package ntnu.idatt2016.v233.SmartMat.util.rsa;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

/**
 * KeyGeneratorUtilsTest is a test class for the KeyGeneratorUtils utility class.
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 *
 */
class KeyGeneratorUtilsTest {

    @Test
    void testGenerateRsaKey() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        assertNotNull(keyPair.getPrivate());
        assertNotNull(keyPair.getPublic());
        assertEquals("RSA", keyPair.getPrivate().getAlgorithm());
        assertEquals("RSA", keyPair.getPublic().getAlgorithm());
        assertNotNull(keyPair.getPrivate().getEncoded());
        assertNotNull(keyPair.getPublic().getEncoded());

    }
}