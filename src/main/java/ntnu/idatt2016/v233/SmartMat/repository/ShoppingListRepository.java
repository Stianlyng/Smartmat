package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface defines the methods for the shopping list repository
 *
 * @author Stian Lyng
 * @version 1.2
 * @since 19.04.2023
 *
 */
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
   
    /**
     * Gets a shopping list by its group ID
     * 
     * @param id the ID of the group
     * @return an optional containing the shopping list if it exists
     */
    Optional<ShoppingList> getByGroupGroupId(long id);


    /**
     * Gets all shopping lists by the username of the user
     * @param username the username of the user
     * @return a list of shopping lists
     */
    List<ShoppingList> findAllByGroupUserUserUsername(String username);


}
