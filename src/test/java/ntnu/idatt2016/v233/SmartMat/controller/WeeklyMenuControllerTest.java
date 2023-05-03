package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
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
        long fridgeId = 1L;
        when(weeklyMenuService.getWeeklyMenu(fridgeId)).thenReturn(weeklyMenu);

        ResponseEntity<List<WeeklyMenuResponse>> response = weeklyMenuController.getWeeklyMenu(fridgeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weeklyMenu, response.getBody());
    }

    @Test
    public void getWeeklyMenu_notFound() {
        long fridgeId = 1L;
        when(weeklyMenuService.getWeeklyMenu(fridgeId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<WeeklyMenuResponse>> response = weeklyMenuController.getWeeklyMenu(fridgeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}