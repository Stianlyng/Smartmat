package ntnu.idatt2016.v233.SmartMat.repository.product;

import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for categories
 *
 * @author Birk
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, String> {
}
