package ntnu.idatt2016.v233.SmartMat.util;


import java.util.UUID;


/**
 * This class provide some useful methods used with the group entity.
 *
 * @author Pedro Cardona
 * @version 1.0
 */
public class GroupUtil {

    /**
     * Calculates the level of a group based on its experience points.
     *
     * @param exp The experience points of the group.
     * @return The level of the group.
     */
    public static int getLevel(long exp){
        return (int) (-3.0 * Math.log(10.0/ ((double)exp)));
    }

    /**
     * Calculates the progress of the current level of a group based on its experience points.
     *
     * @param exp The experience points of the group.
     * @return The progress of the current level of the group as a percentage.
     */
    public static int getProgressOfLevel(long exp) {
        int currentLevel = getLevel(exp);
        double expNextLevel = (10.0 / Math.pow(Math.E, (currentLevel+1) / -3.0));
        double expCurrentLevel = (10.0 / Math.pow(Math.E, (currentLevel) / -3.0));
        double baseNextLevel = expNextLevel - expCurrentLevel;
        double basedCurrentExp= (double) exp - expCurrentLevel;
        return (int) ((basedCurrentExp/ baseNextLevel )* 100.0);
    }

    /**
     * Generates a unique six-digit code using UUID.
     *
     * @return A unique six-digit code.
     */
    public static String generateUniqueCode(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, 6);
    }

}
