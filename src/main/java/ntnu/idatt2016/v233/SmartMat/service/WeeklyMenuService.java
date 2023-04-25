package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.dto.response.WeeklyMenuResponse;
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
    /*
    public List<Object[]> getTop5RecipesWithProducts(long groupId) {
        return recipeRepository.findTop5RecipesWithProducts(groupId);
    }
    */

     /**
     * Retrieves the top 5 recipes with products that have a match with items in the given fridge.
     * Returns a list of RecipeWithProductsDTO objects with recipe details and product information.
     *
     * @param groupId the ID of the fridge to use for matching products
     * @return a list of RecipeWithProductsDTO objects with recipe and product details
     */    
    public List<WeeklyMenuResponse> getTop5RecipesWithProducts(long groupId) {

        List<Object[]> rawData = recipeRepository.findTop5RecipesWithProductsRaw(groupId);

        List<WeeklyMenuResponse> result = rawData.stream()
            .map(row -> new WeeklyMenuResponse(
                (Integer) row[0],
                (String) row[1],
                (Long) row[2],
                (String) row[3],
                (String) row[4],
                (Boolean) row[5]
            ))
            .collect(Collectors.toList());
            
                return result;
            }
}
