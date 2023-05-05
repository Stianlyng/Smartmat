package ntnu.idatt2016.v233.SmartMat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testEquals() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .name("test")
                .build();

        Recipe recipe1 = Recipe.builder()
                .id(1)
                .name("test")
                .build();

        assertEquals(recipe, recipe1);

        recipe1.setId(2);

        assertNotEquals(recipe, recipe1);

        recipe1.setId(1);

        assertEquals(recipe, recipe);

        assertNotEquals(recipe, null);

        assertNotEquals(recipe, new Object());

        assertNotEquals(recipe, new Recipe());

        recipe1.setName("test1");

        assertNotEquals(recipe, recipe1);

    }

    @Test
    void testHashCode() {
        Recipe recipe = Recipe.builder()
                .id(1)
                .name("test")
                .build();

        Recipe recipe1 = Recipe.builder()
                .id(1)
                .name("test")
                .build();

        assertEquals(recipe.hashCode(), recipe1.hashCode());

        recipe1.setId(2);

        assertNotEquals(recipe.hashCode(), recipe1.hashCode());
    }
}