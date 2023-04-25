package ntnu.idatt2016.v233.SmartMat.util;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelUtilsTest {

    @Test
    public void testGetLevel() {
        assertEquals(0, GroupUtil.getLevel(10));
        assertEquals(2, GroupUtil.getLevel(20));
        assertEquals(4, GroupUtil.getLevel(45));
        assertEquals(6, GroupUtil.getLevel(90));
        assertEquals(8, GroupUtil.getLevel(160));
    }

    @Test
    public void testGetPercentOfLevel() {
        assertEquals(0, GroupUtil.getProgressOfLevel(10));
        assertEquals(25, GroupUtil.getProgressOfLevel(11));
        assertEquals(20, GroupUtil.getProgressOfLevel(80));
        assertEquals(10, GroupUtil.getProgressOfLevel(150));
        assertEquals(30, GroupUtil.getProgressOfLevel(225));
        assertEquals(17, GroupUtil.getProgressOfLevel(300));
        assertEquals(62, GroupUtil.getProgressOfLevel(350));
        assertEquals(85, GroupUtil.getProgressOfLevel(375));
    }

}
