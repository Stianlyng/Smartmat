package ntnu.idatt2016.v233.SmartMat.controller.group;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.response.product.RecipeWithMatchCount;
import ntnu.idatt2016.v233.SmartMat.service.product.RecipeService;

/**
 * Controller for weekly menu
 *
 * @author Stian Lyng
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/weeklymenu")
public class WeeklyMenuController {

    final private RecipeService recipeService;

    /**
     * Gets weekly menu for a fridge
     * @param fridgeId the id of the fridge
     * @return the weekly menu for the fridge
     */
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
