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
        // Add WeeklyMenuResponse objects to the weeklyMenu list
        weeklyMenu.add(new WeeklyMenuResponse(1, "Recipe1", 1L, "Product1", "ProductDescription1", true));
        weeklyMenu.add(new WeeklyMenuResponse(2, "Recipe2", 2L, "Product2", "ProductDescription2", false));
    }


    @Test
    public void getWeeklyMenu_found() {
        Long fridgeId = 1L;
        when(weeklyMenuService.getTop5RecipesWithProducts(fridgeId)).thenReturn(weeklyMenu);

        ResponseEntity<List<WeeklyMenuResponse>> response = weeklyMenuController.getWeeklyMenu(fridgeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weeklyMenu, response.getBody());
    }

    @Test
    public void getWeeklyMenu_notFound() {
        Long fridgeId = 1L;
        when(weeklyMenuService.getTop5RecipesWithProducts(fridgeId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<WeeklyMenuResponse>> response = weeklyMenuController.getWeeklyMenu(fridgeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
