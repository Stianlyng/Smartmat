package ntnu.idatt2016.v233.SmartMat.util;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelUtilsTest {

    @Test
    public void testGetLevel() {
        assertEquals(0, LevelUtil.getLevel(10));
        assertEquals(2, LevelUtil.getLevel(20));
        assertEquals(4, LevelUtil.getLevel(45));
        assertEquals(6, LevelUtil.getLevel(90));
        assertEquals(8, LevelUtil.getLevel(160));
    }

    @Test
    public void testGetPercentOfLevel() {
        assertEquals(0, LevelUtil.getProgressOfLevel(10));
        assertEquals(25, LevelUtil.getProgressOfLevel(11));
        assertEquals(20, LevelUtil.getProgressOfLevel(80));
        assertEquals(10, LevelUtil.getProgressOfLevel(150));
        assertEquals(30, LevelUtil.getProgressOfLevel(225));
        assertEquals(17, LevelUtil.getProgressOfLevel(300));
        assertEquals(62, LevelUtil.getProgressOfLevel(350));
        assertEquals(85, LevelUtil.getProgressOfLevel(375));
    }

}
