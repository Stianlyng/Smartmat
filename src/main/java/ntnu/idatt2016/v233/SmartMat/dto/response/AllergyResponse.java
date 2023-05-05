package ntnu.idatt2016.v233.SmartMat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an allergy response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllergyResponse {
    private String name;
    private String description;
}
