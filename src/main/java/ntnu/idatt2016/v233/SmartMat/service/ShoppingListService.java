package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.repository.ShoppingListRepository;

/**
 * Service for the shopping list
 * 
 * @author Stian Lyng
 * @version 1.0
 */
public class ShoppingListService {
    
    ShoppingListRepository shoppingListRepository;
    /**
     * Creates a new ShoppingListService
     * 
     * @param shoppingListRepository The repository to use
     */
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }
    
    /**
     * Saves a shopping list to the database
     * @param shoppingList the shopping list to save
     * @return the saved shopping list
     */
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    /**
     * Gets a shopping list by its ID
     * 
     * @param id the ID of the shopping list
     * @return an optional containing the shopping list if it exists
     */
    public Optional<ShoppingList> getShoppingListById(int id) {
        return shoppingListRepository.getById(id);
    }
    
    /**
     * Gets a shopping list by its group ID
     * 
     * @param id the ID of the group
     * @return an optional containing the shopping list if it exists
     */
    public Optional<ShoppingList> getShoppingListByGroupId(int id) {
        return shoppingListRepository.getByGroupID(id);
    }
    
    /**
     * Gets all shopping lists
     * 
     * @return an optional containing a list of all shopping lists if they exist
     */
    public Optional<List<ShoppingList>> getAllShoppingLists() {
        return shoppingListRepository.getAll();
    } 
    
    /**
     * Gets all shopping lists by group ID
     * 
     * @param id the ID of the group
     * @return an optional containing a list of all shopping lists if they exist
     */
    public Optional<List<ShoppingList>> getAllShoppingListsByGroupId(int id) {
        return shoppingListRepository.getAllByGroupID(id);
    }

    /**
     * Deletes a shopping list by its ID
     * 
     * @param id the ID of the shopping list
     */
    public void deleteShoppingListById(int id) {
        shoppingListRepository.deleteById(id);
    }

}
