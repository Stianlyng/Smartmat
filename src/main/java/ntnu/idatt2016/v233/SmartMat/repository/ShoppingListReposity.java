package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.model.ShoppingList;

/**
 * This interface defines the methods for the shopping list repository
 *
 * @author Stian Lyng
 * @version 1.0
 *
 */
public interface ShoppingListReposity {
    
    /**
     * Saves a shopping list to the database
     * 
     * @param shoppingList the shopping list to save
     */
    ShoppingList save (ShoppingList shoppingList);

    /**
     * Gets a shopping list by its ID
     * 
     * @param id the ID of the shopping list
     * @return an optional containing the shopping list if it exists
     */
    Optional<ShoppingList> getById(int id);

    /**
     * Gets a shopping list by its group ID
     * 
     * @param id the ID of the group
     * @return an optional containing the shopping list if it exists
     */
    Optional<ShoppingList> getByGroupID(int id);

    /**
     * Gets all shopping lists
     * 
     * @return an optional containing a list of all shopping lists
     */
    Optional<List<ShoppingList>> getAll();
    
    /**
     * Gets all shopping lists by group ID
     * 
     * @param id the ID of the group
     * @return an optional containing a list of all shopping lists in the group
     */
    Optional<List<ShoppingList>> getAllByGroupID(int id);
    
    /**
     * Deletes a shopping list by its ID
     * 
     * @param id the ID of the shopping list
     */
    void deleteById(int id);
    
}
