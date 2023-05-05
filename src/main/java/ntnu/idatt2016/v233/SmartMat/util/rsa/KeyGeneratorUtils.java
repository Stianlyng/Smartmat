package ntnu.idatt2016.v233.SmartMat.util.rsa;

import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * KeyGeneratorUtils is a utility class for generating key pairs, specifically RSA key pairs.
 * This class is marked as a Spring component to allow its use in the Spring framework.
 *
 * @author Anders
 * @version 1.0
 */
@Component
final class KeyGeneratorUtils {

    private KeyGeneratorUtils() {}

    /**
     * Generates an RSA key pair of 2048 bits.
     *
     * @return A KeyPair object containing an RSA public key and private key.
     * @throws IllegalStateException If an error occurs during key pair generation.
     */
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
}