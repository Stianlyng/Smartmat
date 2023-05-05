package ntnu.idatt2016.v233.SmartMat.dto.request.product;

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
public class AllergyRequest {

    /**
    * The name of the user.
    */
    private String username;

    /**
     * The unique name of the allergy.
     */
    private String allergyName;
 
}

    
