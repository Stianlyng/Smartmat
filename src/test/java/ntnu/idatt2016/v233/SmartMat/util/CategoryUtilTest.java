package ntnu.idatt2016.v233.SmartMat.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryUtilTest {

    @Test
    void testDefineCategory() {
        String name = "kjøtt";
        String description = "Prime cut of beef";
        assertEquals("meat, fish and chicken", CategoryUtil.defineCategory(name, description));

        name = "brød";
        description = "Whole wheat bread";
        assertEquals("baked goods and grains", CategoryUtil.defineCategory(name, description));

        name = "melk";
        description = "Whole milk";
        assertEquals("dairy and egg ", CategoryUtil.defineCategory(name, description));

        name = "eple";
        description = "Fresh green apple";
        assertEquals("fruit and vegetables", CategoryUtil.defineCategory(name, description));

        name = "Something";
        description = "Unknown product";
        assertEquals("other", CategoryUtil.defineCategory(name, description));
    }

    @Test
    void testGetCategoryName() {
        assertEquals("meat, fish and chicken", CategoryUtil.getCategoryName(1));
        assertEquals("baked goods and grains", CategoryUtil.getCategoryName(2));
        assertEquals("dairy and egg", CategoryUtil.getCategoryName(3));
        assertEquals("other", CategoryUtil.getCategoryName(4));
        assertEquals("fruit and vegetables", CategoryUtil.getCategoryName(5));

        int invalidCategoryNumber = 99;
        assertThrows(IllegalArgumentException.class, () -> CategoryUtil.getCategoryName(invalidCategoryNumber));
    }
}
