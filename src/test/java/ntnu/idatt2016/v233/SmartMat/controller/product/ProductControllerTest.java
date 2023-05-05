package ntnu.idatt2016.v233.SmartMat.controller.product;

import ntnu.idatt2016.v233.SmartMat.dto.request.product.ProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.product.AllergyService;
import ntnu.idatt2016.v233.SmartMat.service.product.CategoryService;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AllergyService allergyService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    private ProductRequest productRequest;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setEan(123L);
        product.setName("Test Product");
        product.setDescription("A test kylling");
        product.setUnit("kg");
        product.setAmount(1.0);

        productRequest = ProductRequest.builder()
                .ean(123L)
                .name("Test Product")
                .description("A test product kylling")
                .image("http://test.com/image.jpg")
                .build();

        List<Allergy> allergies = Arrays.asList(
                Allergy.builder().name("test-allergy").build(),
                Allergy.builder().name("test-allergy2").build()
        );

        List<Product> allergyProducts = Arrays.asList(
                Product.builder().ean(123L).name("Test Product").build(),
                Product.builder().ean(1234L).name("Test Product 2").build()
        );

        product.setAllergies(allergies);

        allergies.forEach(allergy -> allergy.setProducts(allergyProducts));
    }

    @Test
    public void createProduct_productDoesNotExist_returnsCreatedProduct() {
        when(productService.getProductById(123L)).thenReturn(Optional.empty());
        when(productService.getProductVolume(123L)).thenReturn(Optional.of(List.of("1", "kg")));
        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(Category.builder()
                .categoryName("Kjøtt")
                .build()));

        when(allergyService.getAllergyByName(anyString())).thenReturn(Optional.of(Allergy.builder().name("test-allergy").build()));

        // Act
        ResponseEntity<Product> response = productController.createProduct(productRequest);
        Product productResponse = response.getBody();


        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert productResponse != null;
        assertEquals(123L, productResponse.getEan());
        assertEquals("Test Product", productResponse.getName());
        assertEquals("A test product kylling", productResponse.getDescription());
        assertEquals("http://test.com/image.jpg", productResponse.getUrl());
        assertEquals("kg", productResponse.getUnit());
        assertEquals(1.0, productResponse.getAmount());
    }

    @Test
    public void createProduct_productExists_returnsConflict() {
        // Arrange


        when(productService.getProductById(123L)).thenReturn(Optional.of(new Product()));
        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(Category.builder()
                .categoryName("Kjøtt")
                .build()));

        // Act
        ResponseEntity<Product> response = productController.createProduct(productRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void getProduct_productExists_returnsProduct() {


        when(productService.getProductById(123L)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Product> response = productController.getProduct(123L);
        Product result = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert result != null;
        assertEquals(123L, result.getEan());
        assertEquals("Test Product", result.getName());
    }

    @Test
    public void getProduct_productDoesNotExist_returnsNotFound() {
        // Arrange
        when(productService.getProductById(123L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.getProduct(123L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void getAllProducts_returnsAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(),
                new Product(),
                new Product()
        );
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void deleteProduct_productDoesNotExist_returnsNotFound() {
        // Arrange
        when(productService.getProductById(123L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = productController.deleteProduct(123L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }

    @Test
    public void updateProduct_productDoesNotExist_returnsNotFound() {
        // Arrange
        when(productService.getProductById(123L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.updateProduct(123L, productRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getProductByName_productExists_returnsProduct() {
        // Arrange
        when(productService.getProductByName("Test Product")).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Product> response = productController.getProductByName("Test Product");
        Product result = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert result != null;
        assertEquals(123L, result.getEan());
        assertEquals("Test Product", result.getName());
    }

    @Test
    public void getProductByName_productDoesNotExist_returnsNotFound() {
        // Arrange
        when(productService.getProductByName("Nonexistent Product")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.getProductByName("Nonexistent Product");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }


}