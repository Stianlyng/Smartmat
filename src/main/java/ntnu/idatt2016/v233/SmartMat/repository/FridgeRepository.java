package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.Fridge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Products
 * @author Stian
 * @version 1.0
 */
public interface FridgeRepository extends JpaRepository<Product, Long> {

}