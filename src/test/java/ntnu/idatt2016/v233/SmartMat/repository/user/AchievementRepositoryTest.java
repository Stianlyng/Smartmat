package ntnu.idatt2016.v233.SmartMat.repository.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.repository.user.AchievementRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AchievementRepositoryTest {

    @Autowired
    private AchievementRepository achievementRepository;

    private Achievement achievement1;

    @BeforeEach
    void setUp() {
        achievement1 = Achievement.builder()
                .achievementName("Eco Warrior")
                .achievementDescription("Reduce waste by 50% in one month")
                .build();

        achievementRepository.save(achievement1);
    }

    @Test
    void findByAchievementName() {
        Optional<Achievement> result = achievementRepository.findByAchievementName("Eco Warrior");

        assertTrue(result.isPresent());
        assertEquals(achievement1.getAchievementName(), result.get().getAchievementName());
        assertEquals(achievement1.getAchievementDescription(), result.get().getAchievementDescription());
    }
}
