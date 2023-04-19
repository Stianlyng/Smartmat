package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * This is a record class representing an allergy
 * 
 * @author Stian Lyng
 * @version 1.1
 *
 * @param name The name of the allergy
 * @param description The description of the allergy
 */
@Entity
public class Allergy{

    @Id
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
}