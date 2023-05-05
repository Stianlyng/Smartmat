package ntnu.idatt2016.v233.SmartMat.dto.request.group;

/**
 * WasteRequest is a record class representing a request to add a product to a fridge.
 * @param groupId the id of the group
 * @param ean the ean of the product
 * @param amount the amount of the product
 * @param unit the unit of the product
 */
public record WasteRequest(long groupId, long ean, double amount, String unit) {

}
