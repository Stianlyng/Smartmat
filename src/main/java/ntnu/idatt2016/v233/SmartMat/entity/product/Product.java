package ntnu.idatt2016.v233.SmartMat.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;

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

    @ManyToOne
    @JoinColumn(name = "category_name")
    @JsonIgnoreProperties({"products"})
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

    @ManyToMany
    @JsonIgnoreProperties({"products"})
    @JoinTable(
            name = "product_allergy",
            joinColumns = @JoinColumn(name = "ean"),
            inverseJoinColumns = @JoinColumn(name = "allergy_name"))
    List<Allergy> allergies;

    @OneToMany
    @JoinColumn(name = "ean")
    @JsonIgnoreProperties({"products"})
    List<FridgeProductAsso> fridges;
    
    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties({"products"})
    List<Recipe> recipes;

}