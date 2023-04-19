package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.model.product.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Products
 * @author Birk & Stian
 * @version 1.0
 * @since 19.04.2023
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Gets a Product by name
     *
     * @param id the ID of the group
     * @return an optional containing the product if it exists
     */
    Optional<Product> getByProductName(String name);}
