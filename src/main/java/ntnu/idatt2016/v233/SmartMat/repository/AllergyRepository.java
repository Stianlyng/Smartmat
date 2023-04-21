package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for allergies
 * 
 * @author Stian Lyng and birk
 * @version 1.2
 * @since 21.04.2023
 */
public interface AllergyRepository extends JpaRepository<Allergy, String> {
    /**
     * Finds all allergies by product id
     * @param id the id of the product
     * @return list of allergies
     */
    List<Allergy> findAllByProductsEan(long id);
    }
