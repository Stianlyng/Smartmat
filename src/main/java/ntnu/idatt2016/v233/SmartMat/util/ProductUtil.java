package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for products
 * @author Birk, Pedro
 * @version 1.1
 */
public class ProductUtil {

    /**
     * Gets volume from a product
     * @param product the product to get volume from
     * @return an optional containing a list of the volume and unit if it exists
     */
    public static Optional<List<String>> getVolumeFromProduct(Product product) {
        String total = product.getName() + " " + product.getDescription();
        double amount = parseAmount(total);
        String unit = parseUnit(total);
        if (unit.equals("STK") && total.matches(".*\\d+x\\d+.*")) {
            // If no unit was found but the description matches "number x number", assume a multiplication and calculate the result
            Pattern pattern = Pattern.compile("(\\d+)x(\\d+)");
            Matcher matcher = pattern.matcher(total);
            if (matcher.find()) {
                amount = Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        return Optional.of(List.of(amount + "", unit));
    }

    /**
     * Parses unit from a string
     * @param input the string to parse unit from
     * @return the unit
     */
    private static String parseUnit(String input) {
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*(g|kg|l|ml|dl|cl|Kg|L|STK)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String unit = matcher.group(3).toLowerCase();
            if (!unit.equals("stk")) {
                return unit;
            }
        }
        return "STK";
    }

    /**
     * Parses amount from a string
     * @param input the string to parse amount from
     * @return the amount
     */
    private static double parseAmount(String input) {
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*(g|kg|l|ml|dl|cl|Kg|L|STK)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            double amount = Double.parseDouble(matcher.group(1));
            Pattern multiplicationPattern = Pattern.compile("(\\d+)x(\\d+)");
            Matcher multiplicationMatcher = multiplicationPattern.matcher(input);
            if (multiplicationMatcher.find()) {
                amount *= Integer.parseInt(multiplicationMatcher.group(1));
            }
            return amount;
        }
        return 1.0;
    }



    /**
     * Checks if a string contains any numbers
     * @param s The string to check
     * @return True if the string contains any numbers, false otherwise
     */
    private static boolean hasNumbers(String s) {
        return s.matches(".*\\d.*");
    }
}
