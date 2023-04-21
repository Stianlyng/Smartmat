package ntnu.idatt2016.v233.SmartMat.util;


// Formula: 10/(e^(-x/3))
public class LevelUtil {

    public static int getLevel(int exp){
        return (int) (-3.0 * Math.log(10.0/ ((double)exp)));
    }

    public static int getProgressOfLevel(int exp) {
        int currentLevel = getLevel(exp);
        double expNextLevel = (10.0 / Math.pow(Math.E, (currentLevel+1) / -3.0));
        double expCurrentLevel = (10.0 / Math.pow(Math.E, (currentLevel) / -3.0));
        double baseNextLevel = expNextLevel - expCurrentLevel;
        double basedCurrentExp= (double) exp - expCurrentLevel;
        return (int) ((basedCurrentExp/ baseNextLevel )* 100.0);

    }

}
