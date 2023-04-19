package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.Allergy;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for allergies
 * 
 * @author Stian Lyng
 * @version 1.2
 * @since 19.04.2023
 */
public interface AllergyRepository extends JpaRepository<Allergy, String> {

    }
