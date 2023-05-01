package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for fridges API, providing endpoints for fridge management
 *
 * @author Anders Austlid
 * @version 1.0
 * @since 24.04.2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/fridges")
public class FridgeController {

    private final FridgeService fridgeService;

    /**
     * Gets the fridge of a group
     * @param groupId the id of the group
     *                group must exist
     * @return the fridge of the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<Fridge> getFridgeByGroupId(@PathVariable("groupId") long groupId) {
        return fridgeService.getFridgeByGroupId(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Gets the fridge by its fridge id
     * @param fridgeId the id of the fridge
     * @return the fridge if it exists, or a 404 if it doesn't
     */
    @GetMapping("/fridge/{fridgeId}")
    public ResponseEntity<Fridge> getFridgeByFridgeId(@PathVariable("fridgeId") long fridgeId) {
        return fridgeService.getFridgeByFridgeId(fridgeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Adds a product to the fridge of a group
     *
     * @param request the request containing the group id and product id
     * @return success if the product was added, bad request if the product was already in the fridge, or not found if the group or product doesn't exist
     */
    @PostMapping("/group/product")
    public ResponseEntity<Object> addProductToFridge(@RequestBody FridgeProductRequest request) {
        return fridgeService.addProductToFridge(request).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/group/product")
    public ResponseEntity<Object> updateProductInFridge(@RequestBody FridgeProductRequest request) {
        return fridgeService.updateProductInFridge(request).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/group/delete/product/{fridgeProductId}/{amount}")
    public ResponseEntity<?> deleteAmountFridgeProduct(@PathVariable("fridgeProductId") long fridgeProductId,
                                                       @PathVariable("amount") double amount){
        return fridgeService.deleteAmountFromFridge(fridgeProductId,amount).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Deletes a product from the fridge
     * @param fridgeProductId the id of the fridge product association
     * @return success if the product was deleted, bad request if the product wasn't found
     */
    @DeleteMapping("/delete/product/{fridgeProductId}")
    public ResponseEntity<String> removeProductFromFridge(@PathVariable("fridgeProductId") long fridgeProductId) {
        try {
            boolean success = fridgeService.removeProductFromFridge(fridgeProductId);
            if (success){
                return ResponseEntity.ok("Success");
            }
            return ResponseEntity.badRequest().body("Product not found in the fridge");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    /**
     * Deletes a product from the fridge and creates a waste object from it.
     *
     * @param fridgeProductId The id of the fridge product association to be deleted
     * @return A ResponseEntity with status code 200 if successful, or status code 404 if the specified fridge product association was not found.
     */
    @DeleteMapping("/waste/product/{fridgeProductId}")
    public ResponseEntity<?> wasteProductFromFridge(@PathVariable("fridgeProductId") long fridgeProductId){
        return fridgeService.wasteProductFromFridge(fridgeProductId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes all products in a fridge
     * @param fridgeId the id of the fridge
     * @return success if the products were deleted, bad request if the fridge doesn't exist
     *
    @DeleteMapping("/delete/all/{fridgeId}")
    public ResponseEntity<String> deleteAllProductsInFridge(@PathVariable("fridgeId") long fridgeId) {
        try {
            boolean success = fridgeService.deleteAllProductsInFridge(fridgeId);
            if (success){
                return ResponseEntity.ok("Success");
            }
            return ResponseEntity.badRequest().body("Fridge not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    */

}
