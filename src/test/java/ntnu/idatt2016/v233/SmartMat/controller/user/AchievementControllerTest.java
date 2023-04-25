package ntnu.idatt2016.v233.SmartMat.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.entity.group.Achievement;
import ntnu.idatt2016.v233.SmartMat.service.user.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AchievementControllerTest {

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private AchievementController achievementController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Achievement achievement1;
    private Achievement achievement2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(achievementController).build();
        objectMapper = new ObjectMapper();

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
    void getAchievement() throws Exception {
        when(achievementService.getAchievement(achievement1.getAchievementName())).thenReturn(Optional.of(achievement1));

        mockMvc.perform(get("/api/achievements/achievement/{achievementName}", achievement1.getAchievementName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.achievementName").value(achievement1.getAchievementName()))
                .andExpect(jsonPath("$.achievementDescription").value(achievement1.getAchievementDescription()));
    }

    @Test
    void getAchievement_notFound() throws Exception {
        when(achievementService.getAchievement("Unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/achievements/achievement/{achievementName}", "Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAchievements() throws Exception {
        List<Achievement> expectedAchievements = Arrays.asList(achievement1, achievement2);
        when(achievementService.getAchievements()).thenReturn(expectedAchievements);

        mockMvc.perform(get("/api/achievements/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].achievementName").value(achievement1.getAchievementName()))
                .andExpect(jsonPath("$.[0].achievementDescription").value(achievement1.getAchievementDescription()))
                .andExpect(jsonPath("$.[1].achievementName").value(achievement2.getAchievementName()))
                .andExpect(jsonPath("$.[1].achievementDescription").value(achievement2.getAchievementDescription()));
    }

    @Test
    void getAchievements_empty() throws Exception {
        when(achievementService.getAchievements()).thenReturn(List.of());

        mockMvc.perform(get("/api/achievements/all"))
                .andExpect(status().isNotFound());
    }
}
