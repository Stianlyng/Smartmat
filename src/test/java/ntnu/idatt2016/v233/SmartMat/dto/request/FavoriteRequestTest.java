package ntnu.idatt2016.v233.SmartMat.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteRequestTest {

    @Test
    void testEquals() {
        FavoriteRequest favoriteRequest = FavoriteRequest.builder()
                .recipeId(1)
                .username("test")
                .build();

        FavoriteRequest favoriteRequest1 = FavoriteRequest.builder()
                .recipeId(1)
                .username("test")
                .build();

        assertEquals(favoriteRequest, favoriteRequest1);

        favoriteRequest1.setRecipeId(2);

        assertNotEquals(favoriteRequest, favoriteRequest1);

        favoriteRequest1.setRecipeId(1);

        favoriteRequest1.setUsername("test2");

        assertNotEquals(favoriteRequest, favoriteRequest1);

        assertEquals(favoriteRequest, favoriteRequest);

    }

    @Test
    void testHashCode() {
        FavoriteRequest favoriteRequest = FavoriteRequest.builder()
                .recipeId(1)
                .username("test")
                .build();

        FavoriteRequest favoriteRequest1 = FavoriteRequest.builder()
                .recipeId(1)
                .username("test")
                .build();

        assertEquals(favoriteRequest.hashCode(), favoriteRequest1.hashCode());

        favoriteRequest1.setRecipeId(2);

        assertNotEquals(favoriteRequest.hashCode(), favoriteRequest1.hashCode());

    }
}