package ntnu.idatt2016.v233.SmartMat.controller.product;

import ntnu.idatt2016.v233.SmartMat.dto.request.ProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.AllergyService;
import ntnu.idatt2016.v233.SmartMat.service.product.CategoryService;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void createProduct_productDoesNotExist_returnsCreatedProduct() {
        // Arrange
        ProductRequest productRequest = ProductRequest.builder()
                .ean(123L)
                .name("Test Product")
                .description("A test product kylling")
                .image("http://test.com/image.jpg")
                .build();

        when(productService.getProductById(123L)).thenReturn(Optional.empty());
        when(productService.getProductVolume(123L)).thenReturn(Optional.of(List.of("1", "kg")));
        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(Category.builder()
                        .categoryName("Kjøtt")
                .build()));

        // Act
        ResponseEntity<Product> response = productController.createProduct(productRequest);
        Product product = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(123L, product.getEan());
        assertEquals("Test Product", product.getName());
        assertEquals("A test product kylling", product.getDescription());
        assertEquals("http://test.com/image.jpg", product.getUrl());
        assertEquals("kg", product.getUnit());
        assertEquals(1.0, product.getAmount());
    }

    @Test
    public void createProduct_productExists_returnsConflict() {
        // Arrange
        ProductRequest productRequest = ProductRequest.builder()
                .ean(123L)
                .name("Test Product")
                .description("A test product kylling")
                .image("http://test.com/image.jpg")
                .build();

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
        // Arrange
        Product product = new Product();
        product.setEan(123L);
        product.setName("Test Product");
        product.setDescription("A test kylling");

        when(productService.getProductById(123L)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Product> response = productController.getProduct(123L);
        Product result = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
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
}