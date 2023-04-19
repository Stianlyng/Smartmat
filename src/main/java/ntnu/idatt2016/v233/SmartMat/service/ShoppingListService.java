package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.repository.ShoppingListRepository;

/**
 * Service for the shopping list
 * 
 * @author Stian Lyng
 * @version 1.1
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
    public Optional<ShoppingList> getShoppingListById(long id) {
        return shoppingListRepository.findById(id);
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
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    } 
    
    /**
     * Gets all shopping lists by group ID
     * 
     * @param id the ID of the group
     * @return an optional containing a list of all shopping lists if they exist
     */
    public List<ShoppingList> getAllShoppingListsByGroupId(long id) {
        return shoppingListRepository.getAllByGroupID(id);
    }

    /**
     * Deletes a shopping list by its ID
     * 
     * @param id the ID of the shopping list
     */
    public void deleteShoppingListById(long id) {
        shoppingListRepository.deleteById(id);
    }

}
