package ntnu.idatt2016.v233.SmartMat.service.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
        // create a mock product
        Product product = new Product();
        product.setEan(1L);
        product.setName("Test Product");
        Optional<Product> optionalProduct = Optional.of(product);

        // configure the mock repository to return the mock product when findById() is called
        when(productRepositoryMock.findById(1L)).thenReturn(optionalProduct);

        // call the method being tested
        Optional<Product> result = productService.getProductById(1L);

        // verify the result
        assertEquals(optionalProduct, result);
    }

    @Test
    public void testGetAllProducts() {
        // create a mock list of products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setEan(1L);
        product1.setName("Test Product 1");
        Product product2 = new Product();
        product2.setEan(2L);
        product2.setName("Test Product 2");
        products.add(product1);
        products.add(product2);

        // configure the mock repository to return the mock list of products when findAll() is called
        when(productRepositoryMock.findAll()).thenReturn(products);

        // call the method being tested
        List<Product> result = productService.getAllProducts();

        // verify the result
        assertEquals(products, result);
    }

    @Test
    public void testSaveProduct() {
        // create a mock product
        Product product = new Product();
        product.setEan(1L);
        product.setName("Test Product");

        // configure the mock repository to return the mock product when save() is called
        when(productRepositoryMock.save(product)).thenReturn(product);

        // call the method being tested
        Product result = productService.saveProduct(product);

        // verify the result
        assertEquals(product, result);
    }

    @Test
    public void testDeleteProductById() {
        // call the method being tested
        productService.deleteProductById(1L);

        // verify that the deleteById() method was called once on the mock repository with the correct argument
        verify(productRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    public void testGetProductByName() {
        // create a mock product
        Product product = new Product();
        product.setEan(1L);
        product.setName("Test Product");
        Optional<Product> optionalProduct = Optional.of(product);

        // configure the mock repository to return the mock product when getByName() is called
        when(productRepositoryMock.getByName("Test Product")).thenReturn(optionalProduct);

        // call the method being tested
        Optional<Product> result = productService.getProductByName("Test Product");

        // verify the result
        assertEquals(optionalProduct, result);
    }

    @Test
    void testGetProductVolume_existingProduct() {
        long productId = 1L;
        Product product = Product.builder()
                .ean(productId)
                .name("Test Product")
                .description("Test Description 500ml")
                .build();

        // Set up mock repository
        ProductRepository mockRepository = mock(ProductRepository.class);
        when(mockRepository.findById(productId)).thenReturn(Optional.of(product));

        // Set up service with mock repository
        ProductService productService = new ProductService(mockRepository);

        // Verify that the service returns the correct volume
        Optional<List<String>> returnedVolume = productService.getProductVolume(productId);
        assertTrue(returnedVolume.isPresent());
        assertEquals(List.of("500.0", "ml"), returnedVolume.get());
    }

    @Test
    void testGetProductVolume_nonExistingProduct() {
        long productId = 1L;

        // Set up mock repository
        ProductRepository mockRepository = mock(ProductRepository.class);
        when(mockRepository.findById(productId)).thenReturn(Optional.empty());

        // Set up service with mock repository
        ProductService productService = new ProductService(mockRepository);

        // Verify that the service returns an empty optional
        Optional<List<String>> returnedVolume = productService.getProductVolume(productId);
        assertTrue(returnedVolume.isEmpty());
    }


}