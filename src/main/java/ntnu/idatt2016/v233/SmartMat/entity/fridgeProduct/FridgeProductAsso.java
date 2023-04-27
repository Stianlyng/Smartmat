package ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "fridge_product")
@Builder
public class FridgeProductAsso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_product_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "fridge_id")
    @JsonIgnoreProperties({"products"})
    @JsonIgnore
    private Fridge fridgeId;

    @ManyToOne
    @JoinColumn(name = "ean")
    @JsonIgnoreProperties({"fridges"})
    @JsonIgnore
    private Product ean;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "days_to_expiration")
    private int daysToExpiration;

    @Column(name = "amount")
    private int amount;


}
