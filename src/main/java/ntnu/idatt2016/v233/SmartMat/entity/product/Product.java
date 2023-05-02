package ntnu.idatt2016.v233.SmartMat.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Product is an entity class representing a product in the system.
 * All other info about the product is fetched from api call on fronted.
 * this ensures that the product is always up to date.
 * @author Birk and Anders
 * @version 1.1.001
 * @since 19.04.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product")
public class Product{
    @Id
    @Column(name = "ean")
    long ean;
    @Column(name = "item_name")
    String name;
    @Column(name = "description")
    String description;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "shopping_list_product",
            joinColumns = @JoinColumn(name = "ean"),
            inverseJoinColumns = @JoinColumn(name = "shopping_list_id") )
    @JsonIgnore
    List<ShoppingList> shoppingLists;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name")
    @JsonIgnore
    Category category;

    @Column(name = "image_url")
    String url;

    @Column(name = "best_before")
    int bestBefore;

    @Column(name = "expiration_date")
    int expirationDate;

    @Column(name = "unit")
    String unit;

    @Column(name = "amount")
    Double amount;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "products")
    @JsonIgnoreProperties({"products", "users"})
    List<Allergy> allergies;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "ean")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    List<FridgeProductAsso> fridges;
    
    @ManyToMany
    @JoinTable(name = "recipe_product",
            joinColumns = @JoinColumn(name = "ean"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    @JsonIgnore
    List<Recipe> recipes;

    /**
     * Adds a fridge to the product
     * @param fridge the fridge product association to add to the product
     */
    public void addFridge(FridgeProductAsso fridge){
        if(fridges == null){
            fridges = new ArrayList<>();
        }

        fridges.add(fridge);
    }

    @Override
    public String toString(){
        return String.valueOf(this.ean);
    }

    /**
     * Adds a shopping list to the product
     * @param shoppingList the shopping list to add to the product
     */
    public void addShoppingList(ShoppingList shoppingList) {
        if (shoppingLists == null)
            shoppingLists = new ArrayList<>();
        shoppingLists.add(shoppingList);
    }

    public void addAllergy(Allergy allergy){
        if (allergies == null){
            allergies = new ArrayList<>();
        }
        allergies.add(allergy);
    }
}