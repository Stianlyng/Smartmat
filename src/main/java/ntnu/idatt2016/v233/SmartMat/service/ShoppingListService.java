package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.dto.request.ShoppingListRequest;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.repository.ShoppingListRepository;

/**
 * Service for the shopping list
 * 
 * @author Stian Lyng
 * @version 1.1
 */
@Service
public class ShoppingListService {
    
    @Autowired
    ShoppingListRepository shoppingListRepository;
    
    /**
     * Create and save a shopping list to the database
     * @param shoppingList the shopping list to save
     * @return the saved shopping list
     */
    public ShoppingList createShoppingList(ShoppingListRequest shoppingListRequest) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setGroupID(shoppingListRequest.getGroupID());
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
    public Optional<ShoppingList> getShoppingListByGroupId(long id) {
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
        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isPresent()) {
            shoppingListRepository.deleteById(id);
        }
    }
}
