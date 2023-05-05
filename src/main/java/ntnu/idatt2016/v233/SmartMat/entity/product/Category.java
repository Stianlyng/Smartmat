package ntnu.idatt2016.v233.SmartMat.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Category is an entity class representing a product category
 *
 * @author Pedro, Birk
 * @version 1.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "category")
@Data
public class Category {
    @Id
    @Column(name = "category_name")
    String categoryName;
    @Column(name = "category_description")
    String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

    /**
     * adds a product to the category
     * @param product adds a product to the list of products in this category
     */
    public void addProduct(Product product) {
        if (products == null)
            products = List.of(product);
        else if(!products.contains(product))
            products.add(product);
    }
}