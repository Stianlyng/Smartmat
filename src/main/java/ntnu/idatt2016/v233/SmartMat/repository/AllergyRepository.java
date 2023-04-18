package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.model.Allergy;

import java.util.List;
import java.util.Optional;

/**
 * Repository for allergies
 * 
 * @author Stian Lyng
 * @version 1.0
 * @since 04.04.2023
 */
public interface AllergyRepository {
    /**
     * Saves a allergy to the database
     *
     * @param allergy Allergy to save
     */
    Allergy save (Allergy allergy);

    /**
     * Gets an allergy by name
     *
     * @param name the name of the allergy
     * @return an optional containing the Allergy if it exists
     */
    Optional<Allergy> getByName(String name);

    /**
     * Gets all allergies
     *
     * @return an optional containing a list of all allergies
     */
    Optional<List<Allergy>> getAll();


    /**
     * Deletes an allergy by its name
     *
     * @param name the name of the allergy
     */
    void deleteById(String name);

}
