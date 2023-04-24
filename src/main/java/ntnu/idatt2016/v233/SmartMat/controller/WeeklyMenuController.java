package ntnu.idatt2016.v233.SmartMat.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.service.WeeklyMenuService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/weeklymenu")
public class WeeklyMenuController {
    
    private WeeklyMenuService weeklyMenuService;

    @RequestMapping("/getWeeklyMenu/{fridgeId}")
    public ResponseEntity<List<Object[]>> getWeeklyMenu(@PathVariable("fridgeId") Long fridgeId) {
        List<Object[]> weeklyMenu = weeklyMenuService.getTop5RecipesWithProducts(fridgeId);
        
        if (weeklyMenu.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(weeklyMenu);
        }
    }

}
