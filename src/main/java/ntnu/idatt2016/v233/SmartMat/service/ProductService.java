package ntnu.idatt2016.v233.SmartMat.service;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.ProductRepository;
import ntnu.idatt2016.v233.SmartMat.util.ProductUtil;

import java.util.List;
import java.util.Optional;

/**
 * Service for Products
 * uses both the ProductRepository and the ProductUtil
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
public class ProductService {
    ProductRepository productRepository;


    /**
     * Creates a new ProductService
     * @param productRepository The repository to use
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Gets a product by its ID
     * @param id The ID of the product to get
     * @return The product with the given ID, if it exists
     */
    public Optional<Product> getProductById(int id) {
        return productRepository.getById(id);
    }

    /**
     * Gets all products in the database
     * @return All products in the database
     */
    public Optional<List<Product>> getAllProducts() {
        return productRepository.getAll();
    }

    /**
     * Saves a product to the database
     * @param product The product to save
     * @return The saved product
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID
     * @param id The ID of the product to delete
     */
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    /**
     * @param name The name of the product to get
     * @return The product with the given name, if it exists
     */
    public Optional<Product> getProductByName(String name) {
        return productRepository.getByProductName(name);
    }

    /**
     * Gets the volume of a product
     * @param id The id of the product to get the volume from
     * @return The volume of the product, if it exists
     */
    public Optional<String> getProductVolume(int id) {
        if(productRepository.getById(id).isEmpty())
            return Optional.empty();

        return ProductUtil.getVolumeFromProduct(productRepository.getById(id).get());
    }


}
