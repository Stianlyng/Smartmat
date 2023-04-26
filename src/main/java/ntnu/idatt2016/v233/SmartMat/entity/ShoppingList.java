package ntnu.idatt2016.v233.SmartMat.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.List;

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
@Entity(name = "shopping_list")
@Data
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    long shoppingListID;

    @Column(name = "group_id")
    long groupID;

    @ManyToMany
    @JoinTable(
            name = "shopping_list_product",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "ean"))
    @JsonIgnoreProperties("shoppingList")
    private List<Product> products;

}
