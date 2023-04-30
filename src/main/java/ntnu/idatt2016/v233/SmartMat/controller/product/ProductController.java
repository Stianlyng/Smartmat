package ntnu.idatt2016.v233.SmartMat.controller.product;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.ProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.AllergyService;
import ntnu.idatt2016.v233.SmartMat.service.product.CategoryService;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    private final CategoryService categoryService;

    private final AllergyService allergyService;

    /**
     * Creates a product if it does not already exist.
     * @param productRequest The product to be registered.
     * @return The product that was registered.
     */
    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.builder()
                .ean(productRequest.ean())
                .name(productRequest.name())
                .description(productRequest.description())
                .url(productRequest.image())
                .build();

        Category category = categoryService
                .getCategoryByName(CategoryUtil.defineCategory(product.getName(),product.getDescription()))
                .orElse(null);

        if (category == null)
            return ResponseEntity.badRequest().build();

        product.setCategory(category);


        if(productService.getProductById(productRequest.ean()).isPresent())
            return ResponseEntity.status(409).build();


        Optional<List<String>> volumeUnit = productService.getProductVolume(productRequest.ean());

        if(volumeUnit.isPresent()){
            product.setUnit(volumeUnit.get().get(1));
            product.setAmount(Double.parseDouble(volumeUnit.get().get(0)));
        }

        if(productRequest.allergies() != null){
            productRequest.allergies().forEach(allergyName-> product.addAllergy(allergyService.getAllergyByName(allergyName).get()));
        }

        productService.saveProduct(product);
        return ResponseEntity.ok(product);
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

    /**
     * Returns all products in the database.
     * @return All products in the database.
     */
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Deletes a product with the given ean.
     * @param ean The ean of the product to be deleted.
     * @return The product that was deleted.
     */
    @DeleteMapping("ean/{ean}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long ean) {
        Optional<Product> product = productService.getProductById(ean);
        if(product.isPresent()) {
            productService.deleteProductById(product.get().getEan());
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }


    /**
     * Updates a product with the given ean.
     * @param ean The ean of the product to be updated.
     * @param productRequest The product to be updated.
     * @return The product that was updated.
     */
    @PutMapping("ean/{ean}")
    public ResponseEntity<Product> updateProduct(@PathVariable long ean, @RequestBody ProductRequest productRequest) {
        Optional<Product> product = productService.getProductById(ean);
        if(product.isPresent()) {
            product.get().setName(productRequest.name());
            product.get().setDescription(productRequest.description());
            product.get().setUrl(productRequest.image());
            Optional<List<String>> volumeUnit = productService.getProductVolume(productRequest.ean());

            if(volumeUnit.isPresent()){
                product.get().setUnit(volumeUnit.get().get(1));
                product.get().setAmount(Double.parseDouble(volumeUnit.get().get(0)));
            }


            productService.updateProduct(product.get());
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }


}
