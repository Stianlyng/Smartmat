package ntnu.idatt2016.v233.SmartMat.repository.user;

import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for achievements
 *
 * @author Anders
 * @version 1.0
 */
public interface AchievementRepository extends JpaRepository<Achievement, String> {

    /**
     * Finds an achievement by its name
     * @param achievementName the name of the achievement to look up
     * @return an optional containing the achievement if it exists
     */
    Optional<Achievement> findByAchievementName(String achievementName);
}
