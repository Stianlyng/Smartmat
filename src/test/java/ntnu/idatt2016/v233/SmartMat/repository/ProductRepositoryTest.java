package ntnu.idatt2016.v233.SmartMat.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .ean(1234567890123L)
                .name("Test Product")
                .description("This is a test product")
                .categoryName("TestCategory")
                .build();
        entityManager.persist(product);
    }

    @Test
    @DisplayName("Test getProductByName")
    public void testGetProductByName() {
        Optional<Product> foundProduct = productRepository.getByName(product.getName());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
    }

    @Test
    @DisplayName("Test getProductByNameNotFound")
    public void testGetProductByNameNotFound() {
        Optional<Product> foundProduct = productRepository.getByName("Nonexistent Product");
        assertFalse(foundProduct.isPresent());
    }

    @Test
    @DisplayName("Test getAllProducts")
    public void testGetAllProducts() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    @DisplayName("Test saveProduct")
    public void testSaveProduct() {
        Product newProduct = Product.builder()
                .ean(1234567890124L)
                .name("New Product")
                .description("This is a new product")
                .categoryName("TestCategory")
                .build();
        productRepository.save(newProduct);
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Test deleteProduct")
    public void testDeleteProduct() {
        productRepository.delete(product);
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertEquals(0, products.size());
    }

}
