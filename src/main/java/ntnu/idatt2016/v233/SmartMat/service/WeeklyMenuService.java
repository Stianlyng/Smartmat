package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.FridgeRepository;

/**
 * Service class for weekly menu
*/
@Service
public class WeeklyMenuService {
    
    @Autowired
    FridgeRepository fridgeRepository;
    

    public List<Product> getWeeklyMenu(long id) {
        List<Product> products = fridgeRepository.findById(id).get().getProducts();
        // TODO: Cross reference the products with the recipe_products table
        // Now it just returns the products in the fridge
        return products;
        
    }     
    
}
