package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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
    public ResponseEntity<Product> addProductToFridge(@RequestBody FridgeProductRequest request) {
        try {
            return fridgeService.addProductToFridge(request).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/group/product")
    public ResponseEntity<FridgeProductAsso> updateProductInFridge(@RequestBody FridgeProductRequest request) {
        return fridgeService.updateProductInFridge(request).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/group/delete/product/{fridgeProductId}/{amount}")
    public ResponseEntity<?> deleteAmountFridgeProduct(@PathVariable("fridgeProductId") long fridgeProductId,
                                                       @PathVariable("amount") String amountStr, Authentication authentication) {
        Optional<Fridge> fridge = fridgeService.getFridgeByFridgeId(fridgeProductId);

        if (fridge.isEmpty()) {
            return ResponseEntity.status(404).body("Fridge not found");
        }

        if (fridge.get().getGroup().getUser().stream().map(user -> user.getUser().getUsername())
                .noneMatch(username -> username.equals(authentication.getName()))
        && authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name()))){
            return ResponseEntity.status(403).body("You are not a member of this group");
        }

        try {
            double amount = Double.parseDouble(amountStr);

            if (amount < 0.0) {
                return ResponseEntity.badRequest().body("Amount must be greater than or equal to 0.");
            }

            return fridgeService.deleteAmountFromFridge(fridgeProductId, amount)
                    .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid amount format. Please provide a valid number.");
        }
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


}
