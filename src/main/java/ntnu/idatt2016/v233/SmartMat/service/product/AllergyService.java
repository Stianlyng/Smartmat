package ntnu.idatt2016.v233.SmartMat.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2016.v233.SmartMat.dto.response.product.AllergyResponse;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.repository.product.AllergyRepository;

/**
 * Service for allergies
 * @author Stian Lyng
 */
@Service
public class AllergyService {

    @Autowired
    private AllergyRepository allergyRepository;
    
    /**
     * Returns all allergies
     * @return List of allergies
     */
    public List<AllergyResponse> getAllAllergies() {
        List<Allergy> allergies = allergyRepository.findAll();
        return allergies.stream()
            .map(allergy -> AllergyResponse.builder()
                    .name(allergy.getName())
                    .description(allergy.getDescription())
                    .build())
            .collect(Collectors.toList());   
    }
    
    /**
     * Returns allergy by name
     * 
     * @param name Name of allergy
     * @return Optional of allergy
     */
    public Optional<Allergy> getAllergyByName(String name) {
        return allergyRepository.findById(name);
    }
    
    /**
     * Saves allergy
     * @param allergy Allergy to save
     */
    public void saveAllergy(Allergy allergy) {
        allergyRepository.save(allergy);
    }
    
    /**
     * Deletes allergy by name
     * 
     * @param name Name of allergy to delete
     */
    public void deleteAllergyByName(String name) {
        allergyRepository.deleteById(name);
    }
}
