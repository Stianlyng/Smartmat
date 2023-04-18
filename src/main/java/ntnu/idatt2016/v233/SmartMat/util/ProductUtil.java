package ntnu.idatt2016.v233.SmartMat.util;

import ntnu.idatt2016.v233.SmartMat.model.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public static Optional<String> getVolumeFromProduct(Product product) {
        for (String desc : Arrays.asList(product.name(), product.description())) {
            List<String> words = List.of(desc.split(" "));
            if (words.size() > 1) {
                String volume = "";
                for (String unit : VOLUME_UNITS) {
                    int i = words.indexOf(unit);
                    if (i != -1) {
                        return Optional.of(words.get(i - 1) + " " + unit);
                    }
                }
            }
        }

        return Optional.empty();
    }
}
