package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeDetails;
import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeWithMatchCount;
import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;
import ntnu.idatt2016.v233.SmartMat.service.WeeklyMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeeklyMenuControllerTest {

    @InjectMocks
    private WeeklyMenuController weeklyMenuController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private WeeklyMenuService weeklyMenuService;

    private List<WeeklyMenuResponse> weeklyMenu;

    @BeforeEach
    public void setUp() {
        weeklyMenu = new ArrayList<>();
        weeklyMenu.add(WeeklyMenuResponse.builder()
                .recipeId(1)
                .recipeName("Recipe1")
                .recipeDescription("Description1")
                .matchingProducts(10L)
                .build());
        weeklyMenu.add(WeeklyMenuResponse.builder()
                .recipeId(2)
                .recipeName("Recipe2")
                .recipeDescription("Description2")
                .matchingProducts(15L)
                .build());
    }


    @Test
    public void getWeeklyMenu_found() {
        Integer fridgeId = 1;
        List<RecipeWithMatchCount> weeklyMenu = List.of(
            new RecipeWithMatchCount(new RecipeDetails(1, "Recipe 1", "Description 1", "ImageURL 1"), 3),
            new RecipeWithMatchCount(new RecipeDetails(2, "Recipe 2", "Description 2", "ImageURL 2"), 1)
        );

        when(recipeService.getWeeklyMenu(fridgeId)).thenReturn(weeklyMenu);

        ResponseEntity<Object> response = weeklyMenuController.compareWeeklyMenuAndRecipeProducts(fridgeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weeklyMenu, response.getBody());
    }

    @Test
    public void getWeeklyMenu_notFound() {
        Integer fridgeId = 1;
        when(recipeService.getWeeklyMenu(fridgeId)).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = weeklyMenuController.compareWeeklyMenuAndRecipeProducts(fridgeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}