package ntnu.idatt2016.v233.SmartMat.service.user;

import ntnu.idatt2016.v233.SmartMat.service.WeeklyMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeeklyMenuServiceTest {

    @InjectMocks
    private WeeklyMenuService weeklyMenuService;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTop5RecipesWithProducts() {
        long groupId = 1L;

        Object[] row1 = new Object[] {1, "Recipe 1", 10L, "Product 1", "Description 1", true};
        Object[] row2 = new Object[] {2, "Recipe 2", 15L, "Product 2", "Description 2", false};
        List<Object[]> rawData = Arrays.asList(row1, row2);

        when(recipeRepository.findTop5RecipesWithProductsRaw(groupId)).thenReturn(rawData);

        List<WeeklyMenuResponse> result = weeklyMenuService.getTop5RecipesWithProducts(groupId);

        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getRecipeName());
        assertEquals(10L, result.get(0).getEan());
        assertEquals("Product 1", result.get(0).getItemName());
        assertEquals("Recipe 2", result.get(1).getRecipeName());
        assertEquals(15L, result.get(1).getEan());
        assertEquals("Product 2", result.get(1).getItemName());
    }
}
