package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    public ResponseEntity<String> addProductToFridge(@RequestBody FridgeProductRequest request) {
        long groupId = request.groupId();
        long productId = request.productId();

        Optional<Fridge> fridgeOpt = fridgeService.getFridgeByGroupId(groupId);
        if (fridgeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            fridgeService.addProductToFridge(groupId, productId, request.amount(), request.days());
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }


    /**
     * Removes a product from the fridge of a group
     * todo: remove the date parameter when the frontend is done
     *
     * @param groupId the id of the group
     *                group must exist
     *                group must have a fridge
     * @param productId the id of the product
     * @return success if the product was removed, bad request if the product wasn't in the fridge, or not found if the group or product doesn't exist
     */

    /*
    @DeleteMapping("/delete/{fridgeProductId}")
    public ResponseEntity<String> removeProductFromFridge(@PathVariable("fridgeProductId") long FPId) {

        try {
            if (fridgeService.removeProductFromFridge(FPId)){
                return ResponseEntity.ok("Success");
            }
            return ResponseEntity.badRequest().body("Product not found in the fridge");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    */

}
