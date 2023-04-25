package ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * This class represents a fridge product association id
 * @author Birk
 * @version 1.0
 * @since 25.04.2023
 */
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FridgeProductId implements Serializable {
    private long fridgeId;

    private long ean;

    private Date purchaseDate;
}
