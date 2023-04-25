package ntnu.idatt2016.v233.SmartMat.entity.group;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;

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

    @Column(name = "group_id")
    long groupId;
    
    @OneToMany
    @JoinColumn(name = "ean")
    @JsonIgnoreProperties("fridge")
    List<FridgeProductAsso> products;

}
