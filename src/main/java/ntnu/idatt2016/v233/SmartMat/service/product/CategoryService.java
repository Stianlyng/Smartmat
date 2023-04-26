package ntnu.idatt2016.v233.SmartMat.service.product;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for Categories
 * @version 1.0
 * @Author Birk
 * @since 26.04.2020
 */
@Service
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;

    /**
     * gets a category
     * @param name The name of the category
     * @return An optional containing the category if it exists
     */
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findById(name);
    }
}
