package ntnu.idatt2016.v233.SmartMat.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRequestTest {

    @Test
    void testEquals() {

        RecipeRequest recipeRequest = RecipeRequest.builder()
                .recipeDescription("test")
                .recipeName("test")
                .build();

        RecipeRequest recipeRequest1 = RecipeRequest.builder()
                .recipeDescription("test")
                .recipeName("test")
                .build();

        assertEquals(recipeRequest, recipeRequest1);

        recipeRequest1.setRecipeName("test2");

        assertNotEquals(recipeRequest, recipeRequest1);

        recipeRequest1.setRecipeName("test");

        recipeRequest1.setRecipeDescription("test2");

        assertNotEquals(recipeRequest, recipeRequest1);

        assertEquals(recipeRequest, recipeRequest);


    }

    @Test
    void testHashCode() {
        RecipeRequest recipeRequest = RecipeRequest.builder()
                .recipeDescription("test")
                .recipeName("test")
                .build();

        RecipeRequest recipeRequest1 = RecipeRequest.builder()
                .recipeDescription("test")
                .recipeName("test")
                .build();

        assertEquals(recipeRequest.hashCode(), recipeRequest1.hashCode());

        recipeRequest1.setRecipeName("test2");

        assertNotEquals(recipeRequest.hashCode(), recipeRequest1.hashCode());
    }
}