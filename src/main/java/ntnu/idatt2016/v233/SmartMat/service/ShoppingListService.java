package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    ProductRepository productRepository;


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
        return shoppingListRepository.getByGroupGroupId(id);
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

    /**
     * Adds a product to a shopping list
     * @param ean the ean of the product to add
     * @param shoppingListId the id of the shopping list to add the product to
     * @return the product that was added to the shopping list
     */
    public Optional<ShoppingList> addProductToShoppingList(long ean, long shoppingListId){
        shoppingListRepository.findById(shoppingListId).ifPresent(shoppingList -> {
            productRepository.findById(ean).ifPresent(product -> {
                shoppingList.addProduct(product);
                product.addShoppingList(shoppingList);
                shoppingListRepository.save(shoppingList);
            });
        });

        return shoppingListRepository.findById(shoppingListId);
    }

    /**
     * Removes a product from a shopping list
     * @param ean the ean of the product to remove
     * @param shoppingListId the id of the shopping list to remove the product from
     * @return the shopping list that the product was removed from
     */
    public Optional<ShoppingList> removeProductFromShoppingList(long ean, long shoppingListId){
            shoppingListRepository.findById(shoppingListId).ifPresent(shoppingList -> {
            productRepository.findById(ean).ifPresent(product -> {
                shoppingList.getProducts().remove(product);
                product.getShoppingLists().remove(shoppingList);
                productRepository.save(product);
                shoppingListRepository.save(shoppingList);
            });
            });

        return shoppingListRepository.findById(shoppingListId);
    }
}
