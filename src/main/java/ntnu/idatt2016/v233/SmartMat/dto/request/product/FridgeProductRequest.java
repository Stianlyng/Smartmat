package ntnu.idatt2016.v233.SmartMat.dto.request.product;

/**
 * FridgeProductRequest is a record class representing a request to add a product to a fridge.
 * @param fridgeProductId the id of the fridge product
 * @param groupId the id of the group
 * @param ean the ean of the product
 * @param amount the amount of the product
 * @param days the days before expiry date of the product
 * @param price the price of the product
 */
public record FridgeProductRequest(long fridgeProductId, long groupId, long ean, int amount, int days, double price) {
}
