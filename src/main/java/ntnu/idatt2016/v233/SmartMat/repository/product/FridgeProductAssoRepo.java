package ntnu.idatt2016.v233.SmartMat.repository.product;

import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for fridge product associations
 *
 * @author Birk, Pedro
 * @version 1.1
 */
public interface FridgeProductAssoRepo extends JpaRepository<FridgeProductAsso, Long> {
    /**
     * Finds all fridge product associations by id
     * @param id the id of the fridge product association
     * @return an optional holding the fridge product association with the given id if it exists
     */
    Optional<FridgeProductAsso> findAllById(long id);
}
