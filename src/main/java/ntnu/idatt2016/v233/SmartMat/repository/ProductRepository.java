package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.model.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.model.product.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Products
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
public interface ProductRepository {
    /**
     * Saves a Product to the database
     *
     * @param product Product to save
     */
    Product save (Product product);

    /**
     * Gets a Product by its ID
     *
     * @param id the ID of the product
     * @return an optional containing the product if it exists
     */
    Optional<Product> getById(int id);

    /**
     * Gets a Product by name
     *
     * @param id the ID of the group
     * @return an optional containing the product if it exists
     */
    Optional<Product> getByProductName(int id);

    /**
     * Gets all Products
     *
     * @return an optional containing a list of all products
     */
    Optional<List<Product>> getAll();


    /**
     * Deletes a product by its ID
     *
     * @param id the ID of the Product
     */
    void deleteById(int id);

}
