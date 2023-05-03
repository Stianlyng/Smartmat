package ntnu.idatt2016.v233.SmartMat.controller.product;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.ProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.AllergyService;
import ntnu.idatt2016.v233.SmartMat.service.product.CategoryService;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;
import ntnu.idatt2016.v233.SmartMat.util.ProductUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            if(volumeUnit.get().size() > 1){
                product.setUnit(volumeUnit.get().get(1));
                product.setAmount(Double.parseDouble(volumeUnit.get().get(0)));
            }else {
                product.setUnit("STK");
                product.setAmount(1.0);
            }
        }else {
            product.setUnit("STK");
            product.setAmount(1.0);
        }

        if(productRequest.allergies() != null){
            product.setAllergies(new ArrayList<>());

            productRequest.allergies().forEach(allergyName-> {
                allergyService.getAllergyByName(allergyName).ifPresent(allergy -> {
                    product.addAllergy(allergy);
                    allergy.addProduct(product);
                });
            });
            }
        boolean vegan = CategoryUtil.isVegan(product.getName(),product.getDescription());
        if(!vegan){
            Allergy allergy = allergyService.getAllergyByName("Ikke vegansk").get();
            product.addAllergy(allergy);
            allergy.addProduct(product);
            allergy = allergyService.getAllergyByName("Ikke vegetariansk").get();
            product.addAllergy(allergy);
            allergy.addProduct(product);
        }
        boolean vegetarian = CategoryUtil.isVegetarian(product.getName(),product.getDescription(),vegan);
        if(!vegetarian){
            Allergy allergy = allergyService.getAllergyByName("Ikke vegetariansk").get();
            product.addAllergy(allergy);
            allergy.addProduct(product);
        }
        if(!CategoryUtil.isHalal(product.getName(),product.getDescription(),vegetarian)){
            Allergy allergy = allergyService.getAllergyByName("Haram").get()    ;
            product.addAllergy(allergy);
            allergy.addProduct(product);
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
    public ResponseEntity<String> deleteProduct(@PathVariable long ean) {
        Optional<Product> product = productService.getProductById(ean);
        if(product.isPresent()) {
            product.get().getAllergies().stream().filter(allergy -> allergy.getProducts().contains(product.get()))
                    .forEach(allergy -> allergy.getProducts().remove(product.get()));
            product.get().getAllergies().clear();

            productService.deleteProductById(product.get().getEan());
            return ResponseEntity.ok("Product deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
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

            product.get().getAllergies().stream().filter(allergy -> allergy.getProducts().contains(product.get()))
                            .forEach(allergy -> allergy.getProducts().remove(product.get()));
            product.get().getAllergies().clear();


            if(productRequest.allergies() != null){
                productRequest.allergies().forEach(allergyName-> {
                    allergyService.getAllergyByName(allergyName).ifPresent(allergy -> {
                        product.get().addAllergy(allergy);
                        allergy.addProduct(product.get());
                    });
                });
            }

            productService.updateProduct(product.get());
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *
     * Retrieve a product by name using a GET request.
     *
     * @param name The name of the product to retrieve.
     * @return A ResponseEntity containing the product if found, or a 404 Not Found response if not found.
     */
    @GetMapping("name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
