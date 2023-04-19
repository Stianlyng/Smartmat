package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.Achievement;
import ntnu.idatt2016.v233.SmartMat.repository.user.AchievementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for achievements
 *
 * @author Anders
 * @version 1.0
 * @since 19.04.2023
 */

@Service
@AllArgsConstructor
public class AchievementService {

    private AchievementRepository achievementRepository;

    /**
     * Adds an achievement to the database
     * @param achievementName name of achievement to add
     * @param achievementDescription description of achievement to add
     */
    public void addAchievement(String achievementName, String achievementDescription){
        achievementRepository.save(Achievement.builder()
                .achievementName(achievementName)
                .achievementDescription(achievementDescription)
                .build());
    }

    /**
     * Gets an achievement from the database
     * @param achievementName name of achievement to get
     * @return an optional containing the achievement if it exists
     */
    public Optional<Achievement> getAchievement(String achievementName){
        return achievementRepository.findByAchievementName(achievementName);
    }
}
