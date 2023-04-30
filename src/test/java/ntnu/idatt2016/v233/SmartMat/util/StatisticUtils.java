package ntnu.idatt2016.v233.SmartMat.util;

public class StatisticUtils {
    private final static double CO2_KG_MEAT = (99.48 + 39.72 + 33.3 + 26.87 + 12.31 + 9.87 )/6.0;
    private final static double CO2_KG_DAIRY = (23.88 + 4.67 + 3.15)/3;
    private final static double CO2_KG_BAKED = (4.45 + 1.7 + 1.57)/3;
    private final static double CO2_KG_VEGETABLE = (2.09 + 0.98 + 0.86 + 0.46)/4;

    private final static double CO2_KG_OTHER = (CO2_KG_MEAT + CO2_KG_DAIRY  + CO2_KG_BAKED + CO2_KG_VEGETABLE)/4.0;
    
}
