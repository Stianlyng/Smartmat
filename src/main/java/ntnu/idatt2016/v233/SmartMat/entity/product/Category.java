package ntnu.idatt2016.v233.SmartMat.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "category")
@Data
public class Category {
    @Id
    @Column(name = "cateogry_name")
    String ean;
    @Column(name = "category_description")
    String description;
}