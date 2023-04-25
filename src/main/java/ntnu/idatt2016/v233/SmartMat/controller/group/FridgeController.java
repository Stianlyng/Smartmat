package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
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
     * Adds a product to the fridge of a group
     *
     * @param request the request containing the group id and product id
     * @return success if the product was added, bad request if the product was already in the fridge, or not found if the group or product doesn't exist
     */
    @PostMapping("/group/product")
    public ResponseEntity<String> addProductToFridge(@RequestBody FridgeProductRequest request) {
        long groupId = request.groupId();
        long productId = request.productId();

        try {
            fridgeService.getFridgeByGroupId(groupId).orElseThrow();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        if (fridgeService.addProductToFridge(groupId, productId)) {
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Removes a product from the fridge of a group
     *
     * @param groupId the id of the group
     *                group must exist
     *                group must have a fridge
     * @param productId the id of the product
     * @return success if the product was removed, bad request if the product wasn't in the fridge, or not found if the group or product doesn't exist
     */
    @DeleteMapping("/group/{groupId}/product/{productId}")
    public ResponseEntity<String> removeProductFromFridge(@PathVariable("groupId") long groupId, @PathVariable("productId") long productId) {
        try {
            fridgeService.getFridgeByGroupId(groupId).orElseThrow();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        if (fridgeService.removeProductFromFridge(groupId, productId)) {
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().build();
    }

}
