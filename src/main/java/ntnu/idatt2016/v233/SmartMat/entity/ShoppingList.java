package ntnu.idatt2016.v233.SmartMat.entity;


import jakarta.persistence.*;

/**
 * This class represents a shopping list
 * 
 * @author Stian Lyng
 * @version 1.0
 *
 * @param ShoppingListID the id of the shopping list
 * @param GroupID the id of the groupID
 */
@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long ShoppingListID;

    @Column(name = "groupID")
    long groupID;
}
