package ntnu.idatt2016.v233.SmartMat.model.product;

/**
 * Product is a record class representing a product in the system.
 * All other info about the product is fetched from api call on fronted.
 * this ensures that the product is always up to date.
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
public record Product (int ean, String name, String description){
}
