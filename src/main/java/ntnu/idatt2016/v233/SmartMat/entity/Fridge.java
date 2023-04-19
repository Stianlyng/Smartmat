package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "fridgeId")
    long fridgeId;

    @Column(name = "groupId")
    long groupId;
}
