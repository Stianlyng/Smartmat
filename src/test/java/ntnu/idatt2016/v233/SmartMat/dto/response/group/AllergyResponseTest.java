package ntnu.idatt2016.v233.SmartMat.dto.response.group;

import ntnu.idatt2016.v233.SmartMat.dto.response.product.AllergyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllergyResponseTest {

    @Test
    void testEquals() {
        AllergyResponse allergyResponse = AllergyResponse.builder()
                .name("test")
                .description("test")
                .build();

        AllergyResponse allergyResponse1 = AllergyResponse.builder()
                .name("test")
                .description("test")
                .build();

        assertEquals(allergyResponse, allergyResponse1);

        allergyResponse1.setName("test2");

        assertNotEquals(allergyResponse, allergyResponse1);

        allergyResponse1.setName("test");

        allergyResponse1.setDescription("test2");

        assertNotEquals(allergyResponse, allergyResponse1);

        assertEquals(allergyResponse, allergyResponse);

    }

    @Test
    void testHashCode() {
        AllergyResponse allergyResponse = AllergyResponse.builder()
                .name("test")
                .description("test")
                .build();

        AllergyResponse allergyResponse1 = AllergyResponse.builder()
                .name("test")
                .description("test")
                .build();

        assertEquals(allergyResponse.hashCode(), allergyResponse1.hashCode());

        allergyResponse1.setName("test2");

        assertNotEquals(allergyResponse.hashCode(), allergyResponse1.hashCode());
    }
}