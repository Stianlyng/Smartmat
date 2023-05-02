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
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
public class ProductUtil {


    private static final String VOLUME_REGEX = "(\\d+(\\.\\d+)?)\\s*(mls?|centiliters?|deciliters?|liters?|grams?|kilograms?)";
    private static final Map<String, List<String>> VOLUME_UNIT_VARIATIONS = Map.of(
            "ml", List.of("ml", "milliliters", "millilitres"),
            "cl", List.of("cl", "centiliters", "centilitres"),
            "dl", List.of("dl", "deciliters", "decilitres"),
            "l", List.of("l", "liters", "litres"),
            "g", List.of("g", "grams"),
            "kg", List.of("kg", "kilograms")
    );

    public static Optional<List<String>> getVolumeFromProduct(Product product) {
        String desc = product.getName() + " " + product.getDescription();
        Matcher matcher = Pattern.compile(VOLUME_REGEX).matcher(desc);
        if (matcher.find()) {
            String volumeString = matcher.group(1);
            double volume = Double.parseDouble(volumeString);
            String unitString = matcher.group(3);
            for (Map.Entry<String, List<String>> entry : VOLUME_UNIT_VARIATIONS.entrySet()) {
                if (entry.getValue().contains(unitString)) {
                    return Optional.of(List.of(String.valueOf(volume), entry.getKey()));
                }
            }
        }
        return Optional.empty();
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
