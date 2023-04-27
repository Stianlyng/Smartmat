package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * FridgeProductAssoRepository is a repository class for fridge product associations
 * @author Birk
 * @version 1.0
 * @since 25.04.2023
 */

@Repository
public interface FridgeProductAssoRepository extends JpaRepository<FridgeProductAsso, Long> {
    Optional<FridgeProductAsso> findById(long id);
    int findAmountById(long id);

}
