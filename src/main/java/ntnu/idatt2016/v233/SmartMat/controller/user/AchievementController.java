package ntnu.idatt2016.v233.SmartMat.controller.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.Achievement;
import ntnu.idatt2016.v233.SmartMat.service.user.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/achievement")
    public ResponseEntity<Achievement> getAchievement(String achievementName){
        Optional<Achievement> achievement = achievementService.getAchievement(achievementName);
        return achievement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
