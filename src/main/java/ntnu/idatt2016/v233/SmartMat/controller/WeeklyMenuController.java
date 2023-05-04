package ntnu.idatt2016.v233.SmartMat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.response.RecipeWithMatchCount;
import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;
import ntnu.idatt2016.v233.SmartMat.service.WeeklyMenuService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/weeklymenu")
public class WeeklyMenuController {
    
    /*
    private WeeklyMenuService weeklyMenuService;
    
    @GetMapping("/{fridgeId}")
    public ResponseEntity<List<WeeklyMenuResponse>> getWeeklyMenu(@PathVariable("fridgeId") Long fridgeId) {
        List<WeeklyMenuResponse> weeklyMenu = weeklyMenuService.getWeeklyMenu(fridgeId);
        
        if (weeklyMenu.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(weeklyMenu);
        }
    }
    */

    final private RecipeService recipeService;

    @GetMapping("/{fridgeId}")
    public ResponseEntity<Object> compareWeeklyMenuAndRecipeProducts(@PathVariable("fridgeId") Integer fridgeId) {
        List<RecipeWithMatchCount> weeklyMenuDetails = recipeService.getWeeklyMenu(fridgeId);

        if (weeklyMenuDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(weeklyMenuDetails);
        }
    }

}
