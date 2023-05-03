package ntnu.idatt2016.v233.SmartMat.dto.request;

/**
 * FridgeProductRequest is a record class representing a request to add a product to a fridge.
 * @param groupId the id of the group
 */
public record FridgeProductRequest(long fridgeProductId, long groupId, long ean, int amount, int days, double price) {
}
