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
    void testGetWeeklyMenu() {
        long fridgeId = 1L;

        Object[] row1 = new Object[] {1, "Recipe 1", "Description 1", 10L};
        Object[] row2 = new Object[] {2, "Recipe 2", "Description 2", 15L};
        List<Object[]> rawData = Arrays.asList(row1, row2);

        when(recipeRepository.findWeeklyMenu(fridgeId)).thenReturn(rawData);

        List<WeeklyMenuResponse> result = weeklyMenuService.getWeeklyMenu(fridgeId);

        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getRecipeName());
        assertEquals("Description 1", result.get(0).getRecipeDescription());
        assertEquals(10L, result.get(0).getMatchingProducts());
        assertEquals("Recipe 2", result.get(1).getRecipeName());
        assertEquals("Description 2", result.get(1).getRecipeDescription());
        assertEquals(15L, result.get(1).getMatchingProducts());
    }
}