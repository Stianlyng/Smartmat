package ntnu.idatt2016.v233.SmartMat.entity.group;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Fridge is an entity class representing a fridge in the system.
 *
 * @author Anders & Birk
 * @version 1.2
 * @since 25.04.2023
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

    @OneToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("fridge")
    Group group;
    
    @OneToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fridge_id")
    @JsonIgnoreProperties("fridge")
    List<FridgeProductAsso> products;


    /**
     * Adds a product to the fridge
     * @param product the product to add to the fridge
     */
    public void addProduct(FridgeProductAsso product){
        if(products == null){
            products = new ArrayList<>();
        }

        products.add(product);
    }
}
