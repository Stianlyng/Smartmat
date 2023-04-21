package ntnu.idatt2016.v233.SmartMat.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "category")
@Data
public class Category {
    @Id
    @Column(name = "category_name")
    String categoryName;
    @Column(name = "category_description")
    String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}