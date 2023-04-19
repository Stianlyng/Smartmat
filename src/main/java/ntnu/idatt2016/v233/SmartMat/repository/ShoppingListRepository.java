package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface defines the methods for the shopping list repository
 *
 * @author Stian Lyng
 * @version 1.1
 *
 */
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
   
    /**
     * Gets a shopping list by its group ID
     * 
     * @param id the ID of the group
     * @return an optional containing the shopping list if it exists
     */
    Optional<ShoppingList> getByGroupID(long id);
   
    /**
     * Gets all shopping lists by group ID
     * 
     * @param id the ID of the group
     * @return an optional containing a list of all shopping lists in the group
     */
    List<ShoppingList> getAllByGroupID(long id);
   
}
