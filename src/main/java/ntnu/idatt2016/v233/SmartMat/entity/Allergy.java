package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Allergy is an entity class representing an allergy
 * 
 * @author Stian Lyng and Anders
 * @version 1.1.001
 * @since 19.04.2023
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "allergy")
@Data
public class Allergy{

    @Id
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
}