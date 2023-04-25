package ntnu.idatt2016.v233.SmartMat.controller;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.response.AllergyResponse;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.service.AllergyService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for allergies
 * @author Stian Lyng
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/allergies")
public class AllergyController {
    
    /**
     * Service for allergies
     */
    private final AllergyService allergyService;

    /**
     * Gets allergy by name, also includes products that contain the allergy
     *
     * @param name Name of allergy
     * @return Allergies including products that contain the allergy
     */
    @GetMapping("/id/{name}")
    public ResponseEntity<Allergy> getAllergyByName(@PathVariable ("name") String name) {
        return allergyService.getAllergyByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets list of all allergies, without products
     * 
     * @return List of allergies, without products that contain the allergy
     */
    @GetMapping("/all")
    public ResponseEntity<List<AllergyResponse>> getAllAllergies() {
        List<AllergyResponse> allergies = allergyService.getAllAllergies();
        if (allergies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allergies);
    }
    
}
