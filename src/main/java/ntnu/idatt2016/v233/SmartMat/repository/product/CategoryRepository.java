package ntnu.idatt2016.v233.SmartMat.repository.product;

import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
