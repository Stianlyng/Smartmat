package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void CategoryShouldBeSetCorrecltly() {
        Product product = new Product();
        Category category = Category.builder()
                .categoryName("Test5")
                .description("Test Description")
                .build();
        product.setCategory(category);
        productRepository.save(product);

        assertNotNull(product.getCategory());

        Optional<Product> tempProduct = productRepository.findById(product.getEan());
        assertTrue(tempProduct.isPresent());

        assertNotNull(tempProduct.get().getCategory());

        assertNull(tempProduct.get().getCategory().getProducts());
    }


}
