package ntnu.idatt2016.v233.SmartMat.controller.product;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.ProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The product controller is responsible for handling requests related to products.
 * It uses the product service to handle the requests.
 * @version 1.0
 * @Author Birk
 * @since 26.04.2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a product if it does not already exist.
     * @param productRequest The product to be registered.
     * @return The product that was registered.
     */
    @PostMapping("/")
    public ResponseEntity<ProductRequest> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.builder()
                .ean(productRequest.ean())
                .name(productRequest.name())
                .description(productRequest.description())
                .url(productRequest.image())
                .build();

        if(productService.getProductById(productRequest.ean()).isPresent())
            return ResponseEntity.status(409).build();

        productService.saveProduct(product);

        return ResponseEntity.ok(productRequest);

    }

    /**
     * Returns a product with the given ean.
     * @param ean The ean of the product to be returned.
     * @return The product with the given ean.
     */
    @GetMapping("ean/{ean}")
    public ResponseEntity<Product> getProduct(@PathVariable long ean) {
        return productService.getProductById(ean)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
