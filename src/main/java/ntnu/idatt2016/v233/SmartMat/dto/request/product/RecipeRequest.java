package ntnu.idatt2016.v233.SmartMat.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a recipe request
 */
@Data
@Builder
@AllArgsConstructor
public class RecipeRequest {
    /**
     * The unique id of the recipe
     */
    private long id;
    
    /**
     * The name of the recipe
     */
    private String recipeName;
    
    /**
     * The description of the recipe
     */
    private String recipeDescription;
    
}
