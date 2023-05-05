package ntnu.idatt2016.v233.SmartMat.dto.response.product;

import ntnu.idatt2016.v233.SmartMat.dto.response.product.RecipeDetails;
import ntnu.idatt2016.v233.SmartMat.dto.response.product.RecipeWithMatchCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeWithMatchCountTest {

    @Test
    void testEquals() {
        RecipeDetails recipeDetails = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        RecipeDetails recipeDetails1 = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        RecipeWithMatchCount recipeWithMatchCount = RecipeWithMatchCount.builder()
                .recipeDetails(recipeDetails)
                .build();

        RecipeWithMatchCount recipeWithMatchCount1 = RecipeWithMatchCount.builder()
                .recipeDetails(recipeDetails1)
                .build();

        assertEquals(recipeWithMatchCount, recipeWithMatchCount1);

        recipeWithMatchCount1.setMatchCount(123);

        assertNotEquals(recipeWithMatchCount, recipeWithMatchCount1);

        recipeWithMatchCount1.setMatchCount(0);

        assertEquals(recipeWithMatchCount, recipeWithMatchCount1);


    }

    @Test
    void testHashCode() {
        RecipeDetails recipeDetails = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        RecipeDetails recipeDetails1 = RecipeDetails.builder()
                .recipeDescription("test")
                .build();

        RecipeWithMatchCount recipeWithMatchCount = RecipeWithMatchCount.builder()
                .recipeDetails(recipeDetails)
                .build();

        RecipeWithMatchCount recipeWithMatchCount1 = RecipeWithMatchCount.builder()
                .recipeDetails(recipeDetails1)
                .build();

        assertEquals(recipeWithMatchCount.hashCode(), recipeWithMatchCount1.hashCode());

        recipeWithMatchCount1.setMatchCount(123);

        assertNotEquals(recipeWithMatchCount.hashCode(), recipeWithMatchCount1.hashCode());
    }
}