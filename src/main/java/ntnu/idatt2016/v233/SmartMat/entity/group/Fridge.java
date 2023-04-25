package ntnu.idatt2016.v233.SmartMat.entity.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

/**
 * Fridge is an entity class representing a fridge in the system.
 *
 * @author Anders
 * @version 1.1.002
 * @since 15.04.2023
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
    
<<<<<<< HEAD:src/main/java/ntnu/idatt2016/v233/SmartMat/entity/Fridge.java
    @OneToMany
    @JoinColumn(name = "ean")
    @JsonIgnoreProperties("fridge")
    List<FridgeProductAsso> products;
=======
    @ManyToMany
    @JoinTable(name = "fridge_product",
        joinColumns = @JoinColumn(name = "fridge_id"),
        inverseJoinColumns = @JoinColumn(name = "ean"))
    @JsonIgnoreProperties({"allergies", "fridges"})
    List<Product> products;
>>>>>>> 538a2102fb1b5e656159ddd04dad3543ea253234:src/main/java/ntnu/idatt2016/v233/SmartMat/entity/group/Fridge.java
}
