package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a shopping list
 * 
 * @author Stian Lyng, Anders, Birk
 * @version 1.3
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "shopping_list")
@Getter @Setter
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    long shoppingListID;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "shoppingLists")
    @JsonIgnoreProperties("shoppingLists")
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
