package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;

import java.sql.Date;
import java.util.List;


/**
 * A utility class for calculating statistics related to waste and CO2 emissions.
 */
public class StatisticUtil {

    /**
     * Get the annual average amount of CO2 emissions per person.
     *
     * @param co2List The list of CO2 emissions for a group of people.
     * @param numberOfPerson The number of people in the group.
     * @param firstDate The date on which the group started tracking their CO2 emissions.
     * @return The annual average amount of CO2 emissions per person.
     */
    private static double getAnnualAveragePerPerson(List<Double> co2List, int numberOfPerson, Date firstDate) {
        java.util.Date currentDate = new java.util.Date();
        long diffInMillis = currentDate.getTime() - firstDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        double co2Sum = 0.0;
        for (double c02 : co2List) co2Sum += c02;
        return co2Sum / (((double) diffInDays / 365.0) * (double) numberOfPerson);
    }

    /**
     * Get the number of waste items for each category name in a list of waste items.
     *
     * @param wastes The list of waste items for which to calculate the number of items by category name.
     * @return An array of doubles representing the number of waste items for each category name.
     */
    public static double[] getNumberOfWasteByCategoryName(List<Waste> wastes) {
        double[] numberOfWastes = new double[5];
        for (Waste waste : wastes) {
            switch (waste.getEan().getCategory().getCategoryName()) {
                case "meat, fish and chicken" -> numberOfWastes[0] += waste.getAmount();
                case "baked goods and grains" -> numberOfWastes[1] += waste.getAmount();
                case "dairy and egg" -> numberOfWastes[2] += waste.getAmount();
                case "fruit and vegetables" -> numberOfWastes[3] += waste.getAmount();
                default -> numberOfWastes[4] += waste.getAmount();
            }
        }
        return numberOfWastes;
    }
}