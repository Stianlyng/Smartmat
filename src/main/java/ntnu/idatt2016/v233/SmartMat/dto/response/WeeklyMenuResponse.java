package ntnu.idatt2016.v233.SmartMat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a response for a weekly menu.
 */
@Data
@Builder
@AllArgsConstructor
public class WeeklyMenuResponse {

    private long recipeId;
    private String recipeName;
    private long ean;
    private String itemName;
    private String itemDescription;
    private boolean inFridge;
}
