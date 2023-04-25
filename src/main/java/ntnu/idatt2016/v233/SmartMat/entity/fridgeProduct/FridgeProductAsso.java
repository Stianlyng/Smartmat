package ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "fridge_product")
@Builder
@IdClass(FridgeProductId.class)
public class FridgeProductAsso {
    @Id
    @ManyToOne
    @MapsId("fridge_id")
    @JoinColumn(name = "fridge_id")
    private Fridge fridgeId;

    @Id
    @ManyToOne
    @MapsId("ean")
    @JoinColumn(name = "ean")
    private Product ean;

    @Id
    @MapsId("purchase_date")
    @Column(name = "purchase_date")
    private Date purchaseDate;

}
