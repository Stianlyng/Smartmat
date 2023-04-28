package ntnu.idatt2016.v233.SmartMat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a response for a weekly menu.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RecipeFridgeMatch {
    
    private Integer recipeId;
    private String recipeName;
    private String recipeDescription;
    private long ean;
    private String productName;
    private String productDescription;
    private boolean inFridge;

}
