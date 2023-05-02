package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Calculates the sum of waste for each of the four previous months.
     * The amount of waste is converted to kilograms if the unit is in liters, grams, milliliters, centiliters, or deciliters.
     *
     * @param wastes the list of wastes to calculate the sum from
     * @return an array of four doubles representing the sum of waste for each of the last four months,
     *         in the same order as the months are counted backwards from the current month
     */
    public static double[] getNumberOfWasteByLastMonth(List<Waste> wastes){
        double[] result = new double[4];
        HashMap<Integer,List<Waste>> hashMap = new HashMap<>();

        LocalDate localDate = LocalDate.now();
        int currentMonth = localDate.getMonthValue();
        int currentYear = localDate.getYear();
        for(Waste waste : wastes){
            LocalDate localDate1 = waste.getTimestamp().toLocalDateTime().toLocalDate();
            if(currentMonth == localDate1.getMonthValue() && currentYear == localDate1.getYear()){
                hashMap.computeIfAbsent(0, k -> new ArrayList<>());
                hashMap.get(0).add(waste);
            }
            if(Math.abs((currentMonth-1) % 12) == localDate1.getMonthValue() && currentYear == localDate1.getYear()){
                hashMap.computeIfAbsent(1, k -> new ArrayList<>());
                hashMap.get(1).add(waste);
            }
            if(Math.abs((currentMonth-2) % 12) == localDate1.getMonthValue() && currentYear == localDate1.getYear()){
                hashMap.computeIfAbsent(2, k -> new ArrayList<>());
                hashMap.get(2).add(waste);
            }
            if(Math.abs((currentMonth-3) % 12) == localDate1.getMonthValue() && currentYear == localDate1.getYear()){
                hashMap.computeIfAbsent(3, k -> new ArrayList<>());
                hashMap.get(3).add(waste);
            }
        }
        for(int i = 0; i < 4; i++){
            result[i] = getSumOfWaste(hashMap.get(i));
        }
        return result;
    }

    /**
     * Calculates the sum of waste for a list of wastes, and converts the amounts to kilograms if the unit is in liters,
     * grams, milliliters, centiliters, or deciliters.
     *
     * @param wastes the list of wastes to calculate the sum from
     * @return the sum of waste in kilograms
     */
    private static double getSumOfWaste(List<Waste> wastes){
        double sum = 0.0;
        if(wastes == null){
            wastes = new ArrayList<>();
        }
        for(Waste waste: wastes){
            switch (waste.getEan().getUnit()){
                case "l" -> sum += waste.getAmount() * 0.998;
                case "kg" -> sum += waste.getAmount();
                case "g" -> sum += waste.getAmount() * 0.001;
                case "ml" -> sum += waste.getAmount() * 0.000998;
                case "cl" -> sum += waste.getAmount() * 0.00998;
                case "dl" -> sum += waste.getAmount() * 0.0998;
                default -> sum += 0.1 * waste.getAmount();
            }
        }
        return sum;
    }

    /**
     * Calculates the total amount of money lost due to expired or discarded items in the last 30 days,
     * based on the list of wastes passed as a parameter.
     *
     * @param wastes the list of wastes to be analyzed
     * @return the total amount of money lost due to expired or discarded items in the last 30 days
     */
    public static double getLostMoneyInLastMonth(List<Waste> wastes){
        List<Waste> wasteList = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now().minusDays(30);
        for(Waste waste: wastes){
            if(waste.getTimestamp().after(Timestamp.valueOf(time))){
                wasteList.add(waste);
            }
        }
        return lostMoney(wasteList);
    }

    /**
     * Calculates the total amount of money lost due to expired or discarded items,
     * based on the list of wastes passed as a parameter.
     *
     * @param wastes the list of wastes to be analyzed
     * @return the total amount of money lost due to expired or discarded items
     */
    private static double lostMoney(List<Waste> wastes){
        double sum = 0.0;
        for(Waste waste: wastes){
            sum += waste.getAmount()*waste.getBuyPrice();
        }
        return sum;
    }

}