package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Utility class for products
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
public class ProductUtil {


    private static final String[] VOLUME_UNITS = {"ml", "cl", "dl", "l", "g", "kg"};

    /**
     * Gets the volume of a product, if it exists
     * By looking at the name and description of the product
     * it will try to first find the volume in the name, and then in the description
     * It uses pre defined volume units to find the volume
     * Todo: Make it be able to find volume if the size is definde by count
     * @param product The product to get the volume from
     * @return The volume of the product, if it exists
     */
    public static Optional<List<String>> getVolumeFromProduct(Product product) {
        for (String desc : Arrays.asList(product.getName(), product.getDescription())) {
            List<String> words = List.of(desc.split(" "));
            if (words.size() > 1) {
                String volume = "";
                for (String unit : VOLUME_UNITS) {
                    int i = words.indexOf(unit);
                    if (i != -1) {
                        return Optional.of(List.of(words.get(i - 1), unit));
                    }
                }

                volume = words.stream().map(word -> Arrays.stream(VOLUME_UNITS).map(unit -> {
                                    int index = word.indexOf(unit);
                                    if (index == -1) {
                                        if (!Pattern.matches("[a-zA-Z]+", word) && ProductUtil.hasNumbers(word)) {
                                            return word;
                                        }
                                        return "";
                                    }
                                    return word.substring(0, index) + " " + word.substring(index);
                                }).findAny().orElse(""))
                        .filter(ProductUtil::hasNumbers)
                        .findAny()
                        .orElse("");
                if (!volume.equals("")){
                    return Optional.of(List.of(volume.split(" ")));
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
