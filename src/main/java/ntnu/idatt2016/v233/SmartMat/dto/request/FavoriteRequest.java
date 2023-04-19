package ntnu.idatt2016.v233.SmartMat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a request for a shopping list.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {

    /**
     * The unique identifier of the shopping list.
     */
    private long recipeId;

    /**
    * The unique identifier of the group for which the shopping list is requested.
    */
    private String username; 
}
