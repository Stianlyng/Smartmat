package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Recipe is an entity class representing a recipe in the system.
 *
 * @author Anders
 * @version 1.0.001
 * @since 19.04.2023
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
}
