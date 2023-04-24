package ntnu.idatt2016.v233.SmartMat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.repository.RecipeRepository;

/**
 * Service class for weekly menu
*/
@Service
public class WeeklyMenuService {
    
    @Autowired
    RecipeRepository recipeRepository;
     
    public List<Object[]> getTop5RecipesWithProducts(long groupId) {
        return recipeRepository.findTop5RecipesWithProducts(groupId);
    }

}
