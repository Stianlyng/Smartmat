package ntnu.idatt2016.v233.SmartMat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

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

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("group")
    Group group;

    @ManyToOne
    @JoinColumn (name= "ean")
    Product product;

    @Column(name = "timestamp")
    Timestamp timestamp;

    @Column(name = "amount")
    double amount;

    @Column(name = "unit")
    String unit;

}
