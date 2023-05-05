package ntnu.idatt2016.v233.SmartMat.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.response.product.RecipeDetails;

/**
 * This class represents recipe response with match count
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RecipeWithMatchCount {
    
    private RecipeDetails recipeDetails;
    private int matchCount;

}