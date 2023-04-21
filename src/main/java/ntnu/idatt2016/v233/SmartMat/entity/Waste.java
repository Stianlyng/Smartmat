package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * This class represents a waste made by a group.
 *
 * @author Stian Lyng and Anders
 * @version 1.0.001
 * @since 19.04.2023
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Wastes")
@Data
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waste_id")
    long wasteId;

    @Column(name = "group_id")
    long groupId;

    @Column(name = "ean")
    long ean;

    @Column(name = "timestamp")
    Timestamp timestamp;

    @Column(name = "amount")
    double amount;

    @Column(name = "unit")
    String unit;

}
