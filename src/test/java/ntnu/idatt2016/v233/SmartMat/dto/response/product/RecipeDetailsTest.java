package ntnu.idatt2016.v233.SmartMat.dto.response.product;

import ntnu.idatt2016.v233.SmartMat.dto.response.product.RecipeDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDetailsTest {

    @Test
    void testEquals() {

        RecipeDetails recipeDetails = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        RecipeDetails recipeDetails1 = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        assertEquals(recipeDetails, recipeDetails1);

        recipeDetails1.setRecipeDescription("test2");

        assertNotEquals(recipeDetails, recipeDetails1);

        recipeDetails1.setRecipeDescription("test");

        recipeDetails1.setRecipeId(123);

        assertNotEquals(recipeDetails, recipeDetails1);

        assertEquals(recipeDetails, recipeDetails);

    }

    @Test
    void testHashCode() {

            RecipeDetails recipeDetails = RecipeDetails.builder()
                    .recipeDescription("test")
                    .build();

            RecipeDetails recipeDetails1 = RecipeDetails.builder()
                    .recipeDescription("test")
                    .build();

            assertEquals(recipeDetails.hashCode(), recipeDetails1.hashCode());

            recipeDetails1.setRecipeDescription("test2");

            assertNotEquals(recipeDetails.hashCode(), recipeDetails1.hashCode());


    }
}