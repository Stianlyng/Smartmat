package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.*;

/**
 * Fridge is a record class representing a fridge in the system.
 *
 * @author Anders
 * @version 1.1
 * @since 05.04.2023
 *
 * @param fridgeId
 * @param groupId
 */
@Entity
public class Fridge{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridgeId")
    long fridgeId;

    @Column(name = "groupId")
    long groupId;
}
