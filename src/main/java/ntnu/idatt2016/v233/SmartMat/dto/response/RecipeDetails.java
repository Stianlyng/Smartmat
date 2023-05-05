package ntnu.idatt2016.v233.SmartMat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents recipe details response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetails {

    private Integer recipeId;
    private String recipeName;
    private String recipeDescription;
    private String recipeImage;
    
} 