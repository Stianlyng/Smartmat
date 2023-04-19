package ntnu.idatt2016.v233.SmartMat.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a shopping list
 * 
 * @author Stian Lyng and Anders
 * @version 1.0.001
 * @since 19.04.2023
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "shoppinglist")
@Data
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    long ShoppingListID;

    @Column(name = "group_id")
    long groupID;
}
