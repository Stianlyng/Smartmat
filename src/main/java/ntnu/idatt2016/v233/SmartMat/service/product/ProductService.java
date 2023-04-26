package ntnu.idatt2016.v233.SmartMat.service.product;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.ProductRepository;
import ntnu.idatt2016.v233.SmartMat.util.ProductUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Products
 * uses both the ProductRepository and the ProductUtil
 * @author Birk
 * @version 1.1
 * @since 05.04.2023
 */
@Service
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
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Gets all products in the database
     * @return All products in the database
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    /**
     * @param name The name of the product to get
     * @return The product with the given name, if it exists
     */
    public Optional<Product> getProductByName(String name) {
        return productRepository.getByName(name);
    }

    /**
     * Gets the volume of a product
     * @param id The id of the product to get the volume from
     * @return The volume of the product, if it exists
     */
    public Optional<List<String>> getProductVolume(long id) {
        if(productRepository.findById(id).isEmpty())
            return Optional.empty();

        return ProductUtil.getVolumeFromProduct(productRepository.findById(id).get());
    }

    /**
     * Updates a product
     * @param product The product to update
     */
    public void updateProduct(Product product) {
        if(productRepository.findById(product.getEan()).isEmpty())
            return;

        productRepository.save(product);
    }


}
