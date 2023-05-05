package ntnu.idatt2016.v233.SmartMat.dto.request.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a request for a shopping list.
 */
@Data
@Builder
@AllArgsConstructor
public class ShoppingListRequest {
    
    /**
    * The unique identifier of the group for which the shopping list is requested.
    */
    private long groupID; 
}
