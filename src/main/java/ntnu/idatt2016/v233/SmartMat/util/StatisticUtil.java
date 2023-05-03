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
 * @author Pedro Cardona
 * @version 1.0
 * @since 03.05.2023
 */
public class StatisticUtil {

    private final static double CO2_KG_MEAT = (99.48 + 39.72 + 33.3 + 26.87 + 12.31 + 9.87 )/6.0;
    private final static double CO2_KG_DAIRY = (23.88 + 4.67 + 3.15)/3;
    private final static double CO2_KG_BAKED = (4.45 + 1.7 + 1.57)/3;
    private final static double CO2_KG_VEGETABLE = (2.09 + 0.98 + 0.86 + 0.46)/4;
    private final static double CO2_KG_OTHER = (CO2_KG_MEAT + CO2_KG_DAIRY  + CO2_KG_BAKED + CO2_KG_VEGETABLE)/4.0;


    /**
     * Calculates the annual average amount of CO2 emissions per person based on a list of waste objects and a number of persons.
     * @param wastes a list of waste objects
     * @param number the number of persons
     * @return the annual average amount of CO2 emissions per person
     */
    public static double getAnnualAverage(List<Waste> wastes, int number){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Double> CO2 = new ArrayList<>();
        for(Waste waste: wastes){
            if(waste.getTimestamp().before(timestamp)) timestamp = waste.getTimestamp();
            CO2.add(getCO2ByCategory(waste.getEan().getCategory().getCategoryName()) * getCorrectRelation(waste.getEan().getUnit()) * waste.getAmount());
        }
        return getAnnualAveragePerPerson(CO2,number,timestamp);
    }


    /**
     * Returns the amount of CO2 emissions per kg for a given category of waste.
     * @param category the waste category
     * @return the amount of CO2 emissions per kg
     */
    private static double getCO2ByCategory(String category){
        switch (category){
            case "meat, fish and chicken" -> {
                return CO2_KG_MEAT;
            }
            case "baked goods and grains" -> {
                return CO2_KG_BAKED;
            }
            case "dairy and egg" ->{
                return CO2_KG_DAIRY;
            }
            case "fruit and vegetables" ->{
                return CO2_KG_VEGETABLE;
            }
            default -> {
                return CO2_KG_OTHER;
            }
        }
    }

    /**
     * Returns the correct CO2 relation for a given unit of waste.
     * @param unit the unit of waste
     * @return the correct CO2 relation
     */
    private static double getCorrectRelation(String unit){
        switch (unit){
            case "l" -> {
                return 0.998;
            }
            case "kg" -> {
                return 1.0;
            }
            case "g" -> {
                return 0.001;
            }
            case "ml" -> {
                return 0.000998;
            }
            case "cl" -> {
                return 0.00998;
            }
            case "dl" -> {
                return 0.0998;
            }
            default -> {
                return 0.1;
            }
        }
    }

    /**
     * Get the annual average amount of CO2 emissions per person.
     *
     * @param co2List The list of CO2 emissions for a group of people.
     * @param numberOfPerson The number of people in the group.
     * @param firstDate The date on which the group started tracking their CO2 emissions.
     * @return The annual average amount of CO2 emissions per person.
     */
    private static double getAnnualAveragePerPerson(List<Double> co2List, int numberOfPerson, Timestamp firstDate) {
        java.util.Date currentDate = new java.util.Date();
        long diffInMillis = currentDate.getTime() - firstDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        double co2Sum = 0.0;
        for (double c02 : co2List) co2Sum += c02;
        return co2Sum / (((double) (diffInDays +1) / 365.0) * (double) numberOfPerson);
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
            sum += getCorrectRelation(waste.getEan().getUnit()) * waste.getAmount();
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