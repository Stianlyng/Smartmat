package ntnu.idatt2016.v233.SmartMat.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

/**
 * Fridge is an entity class representing a fridge in the system.
 *
 * @author Anders
 * @version 1.1.001
 * @since 19.04.2023
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "fridge")
@Data
public class Fridge{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    long fridgeId;

    @Column(name = "group_id")
    long groupId;
    
    @ManyToMany
    @JoinTable(name = "fridge_product",
        joinColumns = @JoinColumn(name = "fridge_id"),
        inverseJoinColumns = @JoinColumn(name = "ean"))
    @JsonIgnoreProperties("fridges")
    List<Product> products;
}
