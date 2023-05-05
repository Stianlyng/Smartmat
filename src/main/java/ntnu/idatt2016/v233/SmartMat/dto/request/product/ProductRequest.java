package ntnu.idatt2016.v233.SmartMat.dto.request.product;

import lombok.Builder;

import java.util.List;

/**
 * ProductRequest is a record class representing a request to create a product.
 * @param ean the ean of the product
 * @param name the name of the product
 * @param description the description of the product
 * @param image the image of the product
 * @param price the price of the product
 * @param allergies the allergies of the product
 */
@Builder
public record ProductRequest(long ean, String name, String description, String image, double price, List<String> allergies) {
}
