package ntnu.idatt2016.v233.SmartMat.entity.group;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementTest {

    @Test
    void testEquals() {
        Achievement achievement = Achievement.builder()
                .achievementName("test")
                .build();

        Achievement achievement1 = Achievement.builder()
                .achievementName("test")
                .build();

        assertEquals(achievement, achievement1);

        achievement1.setAchievementName("test1");

        assertNotEquals(achievement, achievement1);

        achievement1.setAchievementName("test");

        assertEquals(achievement, achievement);

        assertNotEquals(achievement, null);

        assertNotEquals(achievement, new Object());

        assertNotEquals(achievement, new Achievement());


    }

    @Test
    void testHashCode() {
        Achievement achievement = Achievement.builder()
                .achievementName("test")
                .build();

        Achievement achievement1 = Achievement.builder()
                .achievementName("test")
                .build();

        assertEquals(achievement.hashCode(), achievement1.hashCode());

        achievement1.setAchievementName("test1");

        assertNotEquals(achievement.hashCode(), achievement1.hashCode());
    }
}