package ntnu.idatt2016.v233.SmartMat.service.user;

import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.repository.user.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementService achievementService;

    private Achievement achievement1;
    private Achievement achievement2;

    @BeforeEach
    void setUp() {
        achievement1 = Achievement.builder()
                .achievementName("Achievement 1")
                .achievementDescription("Achievement 1 description")
                .build();

        achievement2 = Achievement.builder()
                .achievementName("Achievement 2")
                .achievementDescription("Achievement 2 description")
                .build();
    }

    @Test
    void getAchievement() {
        when(achievementRepository.findByAchievementName(achievement1.getAchievementName())).thenReturn(Optional.of(achievement1));

        Optional<Achievement> result = achievementService.getAchievement(achievement1.getAchievementName());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(achievement1);
        verify(achievementRepository).findByAchievementName(achievement1.getAchievementName());
    }

    @Test
    void getAchievement_notFound() {
        when(achievementRepository.findByAchievementName("Unknown")).thenReturn(Optional.empty());

        Optional<Achievement> result = achievementService.getAchievement("Unknown");

        assertThat(result).isNotPresent();
        verify(achievementRepository).findByAchievementName("Unknown");
    }

    @Test
    void getAchievements() {
        List<Achievement> expectedAchievements = Arrays.asList(achievement1, achievement2);
        when(achievementRepository.findAll()).thenReturn(expectedAchievements);

        List<Achievement> result = achievementService.getAchievements();

        assertThat(result).isEqualTo(expectedAchievements);
        verify(achievementRepository).findAll();
    }
}
