package ntnu.idatt2016.v233.SmartMat.controller.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.service.user.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Achievement API endpoint, providing endpoints for fetching an achievement by name,
 * and fetching all achievements stored in the database
 *
 * @author Anders
 * @version 1.0
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    /**
     * Gets an achievement from the database
     * @param achievementName name of achievement to get
     * @return a ResponseEntity containing the achievement if it exists, or a 404 if it doesn't
     */
    @GetMapping("/achievement/{achievementName}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable("achievementName") String achievementName){
        Optional<Achievement> achievement = achievementService.getAchievement(achievementName);
        return achievement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets all achievements from the database
     * @return a ResponseEntity containing a list of all achievements, or a 404 if there are none
     */
    @GetMapping("/all")
    public ResponseEntity<List<Achievement>> getAchievements(){
        List<Achievement> achievements = achievementService.getAchievements();
        return achievements.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(achievements);
    }
}
