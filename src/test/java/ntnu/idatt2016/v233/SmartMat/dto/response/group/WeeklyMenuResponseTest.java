package ntnu.idatt2016.v233.SmartMat.dto.response.group;

import ntnu.idatt2016.v233.SmartMat.dto.response.group.WeeklyMenuResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyMenuResponseTest {

    @Test
    void testEquals() {
        WeeklyMenuResponse weeklyMenuResponse = WeeklyMenuResponse.builder()
                .recipeDescription("test")
                .matchingProducts(123L)
                .build();

        WeeklyMenuResponse weeklyMenuResponse1 = WeeklyMenuResponse.builder()
                .recipeDescription("test")
                .matchingProducts(123L)
                .build();

        assertEquals(weeklyMenuResponse, weeklyMenuResponse1);

        weeklyMenuResponse1.setRecipeDescription("test2");

        assertNotEquals(weeklyMenuResponse, weeklyMenuResponse1);

        weeklyMenuResponse1.setRecipeDescription("test");

        weeklyMenuResponse1.setMatchingProducts(321L);

        assertNotEquals(weeklyMenuResponse, weeklyMenuResponse1);

        weeklyMenuResponse1.setMatchingProducts(123L);

        weeklyMenuResponse1.setRecipeId(123);

        assertNotEquals(weeklyMenuResponse, weeklyMenuResponse1);

        assertEquals(weeklyMenuResponse, weeklyMenuResponse);

    }

    @Test
    void testHashCode() {
        WeeklyMenuResponse weeklyMenuResponse = WeeklyMenuResponse.builder()
                .recipeDescription("test")
                .matchingProducts(123L)
                .build();

        WeeklyMenuResponse weeklyMenuResponse1 = WeeklyMenuResponse.builder()
                .recipeDescription("test")
                .matchingProducts(123L)
                .build();

        assertEquals(weeklyMenuResponse.hashCode(), weeklyMenuResponse1.hashCode());

        weeklyMenuResponse1.setRecipeDescription("test2");

        assertNotEquals(weeklyMenuResponse.hashCode(), weeklyMenuResponse1.hashCode());

        weeklyMenuResponse1.setRecipeDescription("test");

        weeklyMenuResponse1.setMatchingProducts(321L);

        assertNotEquals(weeklyMenuResponse.hashCode(), weeklyMenuResponse1.hashCode());

        weeklyMenuResponse1.setMatchingProducts(123L);

        weeklyMenuResponse1.setRecipeId(123);

        assertNotEquals(weeklyMenuResponse.hashCode(), weeklyMenuResponse1.hashCode());

        assertEquals(weeklyMenuResponse.hashCode(), weeklyMenuResponse.hashCode());
    }
}