package ntnu.idatt2016.v233.SmartMat.dto.request.product;

import ntnu.idatt2016.v233.SmartMat.dto.request.product.AllergyRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllergyRequestTest {

    @Test
    void testEquals() {
        AllergyRequest allergyRequest = AllergyRequest.builder()
                .allergyName("test")
                .username("test")
                .build();

        AllergyRequest allergyRequest1 = AllergyRequest.builder()
                .allergyName("test")
                .username("test")
                .build();

        assertEquals(allergyRequest, allergyRequest1);

        allergyRequest1.setAllergyName("test2");

        assertNotEquals(allergyRequest, allergyRequest1);

        allergyRequest1.setAllergyName("test");

        allergyRequest1.setUsername("test2");

        assertNotEquals(allergyRequest, allergyRequest1);

        assertEquals(allergyRequest, allergyRequest);

    }

    @Test
    void testHashCode() {
        AllergyRequest allergyRequest = AllergyRequest.builder()
                .allergyName("test")
                .username("test")
                .build();

        AllergyRequest allergyRequest1 = AllergyRequest.builder()
                .allergyName("test")
                .username("test")
                .build();

        assertEquals(allergyRequest.hashCode(), allergyRequest1.hashCode());

        allergyRequest1.setAllergyName("test2");

        assertNotEquals(allergyRequest.hashCode(), allergyRequest1.hashCode());
    }
}