package ntnu.idatt2016.v233.SmartMat.repository.product;

import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FridgeProductAssoRepo extends JpaRepository<FridgeProductAsso, Long> {
    Optional<FridgeProductAsso> findAllById(long id);
}
