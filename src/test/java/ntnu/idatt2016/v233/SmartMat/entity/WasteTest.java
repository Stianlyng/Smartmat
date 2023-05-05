package ntnu.idatt2016.v233.SmartMat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WasteTest {

    @Test
    void testEquals() {
        Waste waste = Waste.builder()
                .wasteId(1)
                .amount(1)
                .build();

        Waste waste1 = Waste.builder()
                .wasteId(1)
                .amount(1)
                .build();

        assertEquals(waste, waste1);

        waste1.setAmount(2);

        assertNotEquals(waste, waste1);

        waste1.setAmount(1);

        assertEquals(waste, waste);

        assertNotEquals(waste, null);

        assertNotEquals(waste, new Object());

        assertNotEquals(waste, new Waste());

        waste1.setWasteId(2);

        assertNotEquals(waste, waste1);

        waste1.setWasteId(1);

        assertEquals(waste, waste1);

    }

    @Test
    void testHashCode() {
        Waste waste = Waste.builder()
                .wasteId(1)
                .amount(1)
                .build();

        Waste waste1 = Waste.builder()
                .wasteId(1)
                .amount(1)
                .build();

        assertEquals(waste.hashCode(), waste1.hashCode());

        waste1.setAmount(2);

        assertNotEquals(waste.hashCode(), waste1.hashCode());

        waste1.setAmount(1);

        assertEquals(waste.hashCode(), waste.hashCode());

        waste1.setWasteId(2);

        assertNotEquals(waste.hashCode(), waste1.hashCode());
    }
}