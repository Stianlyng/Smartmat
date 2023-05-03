package ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "fridge_product")
@Builder
public class FridgeProductAsso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_product_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fridge_id")
    @JsonIgnore
    private Fridge fridgeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ean")
    @JsonIncludeProperties({"HibernateLazyInitializer", "handler", "ean", "name", "description", "category",
            "url", "allergies", "bestBefore", "expirationDate", "unit", "amount"})
    private Product ean;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "days_to_expiration")
    private int daysToExpiration;

    @Column(name = "amount")
    private double amount;

    @Column(name = "buy_price")
    private double buyPrice;


}
