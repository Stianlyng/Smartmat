package ntnu.idatt2016.v233.SmartMat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
import ntnu.idatt2016.v233.SmartMat.service.WeeklyMenuService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/weeklymenu")
public class WeeklyMenuController {
    
    private WeeklyMenuService weeklyMenuService;

    @GetMapping("/getWeeklyMenu/{fridgeId}")
    public ResponseEntity<List<WeeklyMenuResponse>> getWeeklyMenu(@PathVariable("fridgeId") Long fridgeId) {
        List<WeeklyMenuResponse> weeklyMenu = weeklyMenuService.getTop5RecipesWithProducts(fridgeId);
        
        if (weeklyMenu.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(weeklyMenu);
        }
    }

}
