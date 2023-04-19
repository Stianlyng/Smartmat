package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.*;

/**
 * Recipe is a record class representing a recipe in the system.
 *
 * @author Anders
 * @version 1.0
 * @since 04.04.2023
 *
 * @param id
 * @param name
 * @param description
 */
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;
}
