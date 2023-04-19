package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.model.Allergy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for allergies
 * 
 * @author Stian Lyng
 * @version 1.0
 * @since 04.04.2023
 */
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, String> {

    /**
     * Gets an allergy by name
     *
     * @param name the name of the allergy
     * @return an optional containing the Allergy if it exists
     */
    List<Allergy> findByName(String name);

    }
