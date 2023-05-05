package ntnu.idatt2016.v233.SmartMat.service.group;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.dto.response.group.WeeklyMenuResponse;
import ntnu.idatt2016.v233.SmartMat.repository.product.RecipeRepository;

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
     * Returns a list of RecipeWithProductsDTO objects with recipe details and product information.
     *
     * @param fridgeId the ID of the fridge to use for matching products
     * @return a list of RecipeWithProductsDTO objects with recipe and product details
     */    
    public List<WeeklyMenuResponse> getWeeklyMenu(long fridgeId) {
    
        List<Object[]> rawData = recipeRepository.findWeeklyMenu(fridgeId);

        List<WeeklyMenuResponse> result = rawData.stream()
            .map(row -> new WeeklyMenuResponse(
                (Integer) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (long) row[4]
            ))
            .collect(Collectors.toList());
            
                return result;
            }
    
}