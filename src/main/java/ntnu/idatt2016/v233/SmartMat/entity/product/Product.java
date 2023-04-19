package ntnu.idatt2016.v233.SmartMat.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Product is a record class representing a product in the system.
 * All other info about the product is fetched from api call on fronted.
 * this ensures that the product is always up to date.
 * @author Birk
 * @version 1.1
 * @since 05.04.2023
 */
@Builder
@Entity(name = "product")
@Data
public class Product{
    @Id
    @Column(name = "ean")
    long ean;
    @Column(name = "item_name")
    String name;
    @Column(name = "description")
    String description;
}
