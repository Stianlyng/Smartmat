package ntnu.idatt2016.v233.SmartMat.entity;

import java.util.List;

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
 * @author Anders & Stian + Birk
 * @version 1.1
 * @since 25.04.2023
 *
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
    
    @ManyToMany
    @JoinTable(name = "recipe_product",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "ean"))
    @JsonIgnoreProperties({"recipes"})
    List<Product> products;

    @ManyToMany(mappedBy = "recipes")
    @JsonIgnoreProperties({"recipes"})
    List<User> users;
    
}
