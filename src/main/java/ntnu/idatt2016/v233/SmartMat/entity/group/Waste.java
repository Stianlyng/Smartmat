package ntnu.idatt2016.v233.SmartMat.entity.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @author Anders, Birk, Pedro
 * @version 1.3
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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    @JsonIgnore
    Group groupId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn (name= "ean")
    @JsonIgnore
    Product ean;

    @Column(name = "timestamp")
    Timestamp timestamp;

    @Column(name = "amount")
    double amount;

    @Column(name = "unit")
    String unit;

    @Column(name = "buy_price")
    double buyPrice;

}
