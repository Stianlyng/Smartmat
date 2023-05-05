package ntnu.idatt2016.v233.SmartMat.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

/**
 * Recipe is an entity class representing a recipe in the system.
 *
 * @author Anders, Stian, Birk
 * @version 1.7
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recipe")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    long id;

    @Column(name = "recipe_name")
    String name;

    @Column(name = "recipe_description")
    String description;
    
    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "guide")
    String guide;
    
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
            ,mappedBy = "recipes"
    )
    @JsonIgnoreProperties({"recipes"})
    List<Product> products;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "favorite_recipes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    @JsonIgnore
    List<User> users;


    /**
     * Adds a product to the recipe
     * @param product product to add
     */
    public void addProduct(Product product){

        if(products ==  null){
            products = new ArrayList<>();
        }

        products.add(product);
    }

    /**
     * Adds a user to the recipe
     * used for adding favorites
     * @param user user to add
     */
    public void addUser(User user){

        if(users ==  null){
            users = new ArrayList<>();
        }

        users.add(user);
    }
}
