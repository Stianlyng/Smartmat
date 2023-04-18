package ntnu.idatt2016.v233.SmartMat.model;


/**
 * This class represents a shopping list
 * 
 * @author Stian Lyng
 * @version 1.0
 *
 * @param ShoppingListID the id of the shopping list
 * @param GroupID the id of the groupID
 */
public record ShoppingList(long ShoppingListID, long groupID) {
}
