package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;

/**
 * Service class for weekly menu
 * 
 * @author Stian Lyng
 * @version 1.0
*/
@Service
public class WeeklyMenuService {
    
    @Autowired
    RecipeRepository recipeRepository;

     /**
     * Retrieves the top 5 recipes with products that have a match with items in the given fridge.
     * Returns a list of Object arrays, where each array contains the recipe details and product information.
     *
     * @param groupId the ID of the fridge to use for matching products
     * @return a list of Object arrays with recipe and product details
     */    
    public List<Object[]> getTop5RecipesWithProducts(long groupId) {
        return recipeRepository.findTop5RecipesWithProducts(groupId);
    }

}
