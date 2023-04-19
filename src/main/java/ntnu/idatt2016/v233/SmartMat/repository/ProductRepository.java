package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Products
 * @author Birk & Stian
 * @version 1.0
 * @since 19.04.2023
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Gets a Product by name
     *
     * @param name the name of the group
     * @return an optional containing the product if it exists
     */
    Optional<Product> getByName(String name);}
