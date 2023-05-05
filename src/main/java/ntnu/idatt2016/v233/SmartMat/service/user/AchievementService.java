package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.repository.user.AchievementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for achievements
 *
 * @author Anders
 * @version 1.0
 */

@Service
@AllArgsConstructor
public class AchievementService {

    private AchievementRepository achievementRepository;

    /**
     * Gets an achievement from the database
     * @param achievementName name of achievement to get
     * @return an optional containing the achievement if it exists
     */
    public Optional<Achievement> getAchievement(String achievementName){
        return achievementRepository.findByAchievementName(achievementName);
    }

    /**
     * Gets all achievements from the database
     * @return a list of all achievements
     */
    public List<Achievement> getAchievements(){
        return achievementRepository.findAll();
    }
}
