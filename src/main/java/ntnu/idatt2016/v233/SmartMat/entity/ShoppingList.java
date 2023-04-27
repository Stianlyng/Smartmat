package ntnu.idatt2016.v233.SmartMat.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.ArrayList;
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


    @OneToOne(cascade = CascadeType.ALL)
    private Group group;

    @ManyToMany
    @JoinTable(
            name = "shopping_list_product",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "ean"))
    @JsonIgnoreProperties("shoppingList")
    private List<Product> products;


    /**
     * Adds a product to the shopping list
     * @param product  the product to add to the shopping list
     */
    public void addProduct(Product product) {
        if (products == null)
            products = new ArrayList<>();
        products.add(product);
    }
}
