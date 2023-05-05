package ntnu.idatt2016.v233.SmartMat.entity.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    @Test
    void testEquals() {
        Fridge fridge = Fridge.builder()
                .fridgeId(1)
                .build();

        Fridge fridge1 = Fridge.builder()
                .fridgeId(1)
                .build();

        assertEquals(fridge, fridge1);

        fridge1.setFridgeId(2);

        assertNotEquals(fridge, fridge1);

        assertEquals(fridge, fridge);

        assertNotEquals(fridge, null);

        assertNotEquals(fridge, new Object());

        assertNotEquals(fridge, new Fridge());

        fridge1.setFridgeId(1);

        assertEquals(fridge, fridge1);
    }

    @Test
    void testHashCode() {
        Fridge fridge = Fridge.builder()
                .fridgeId(1)
                .build();

        Fridge fridge1 = Fridge.builder()
                .fridgeId(1)
                .build();

        assertEquals(fridge.hashCode(), fridge1.hashCode());

        fridge1.setFridgeId(2);

        assertNotEquals(fridge.hashCode(), fridge1.hashCode());
    }
}