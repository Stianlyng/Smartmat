package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.product.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Controller for fridges API, providing endpoints for fridge management
 *
 * @author Anders Austlid & Birk
 * @version 2.0
 * @since 5.05.2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/fridges")
public class FridgeController {

    private final FridgeService fridgeService;

    private final GroupService groupService;


    /**
     * Gets the fridge of a group
     * @param groupId the id of the group must exist
     * @return the fridge of the group if it exists, or a 404 if it doesn't exist or the user is not in the group
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<Fridge> getFridgeByGroupId(@PathVariable("groupId") long groupId, Authentication authentication) {
        Optional<Fridge> fridge = fridgeService.getFridgeByGroupId(groupId);

        if (fridge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!fridgeService.isUserInFridge(authentication.getName(), fridge.get().getFridgeId())
                && !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return fridge.map(ResponseEntity::ok).get();
    }


    /**
     * Gets the fridge by its fridge id
     * @param fridgeId the id of the fridge to get
     * @param authentication a user authentication object for validation of user authorization
     * @return the fridge if it exists, or a 404 if it doesn't, or a 403 if the user is not in the fridge
     */
    @GetMapping("/fridge/{fridgeId}")
    public ResponseEntity<Fridge> getFridgeByFridgeId(@PathVariable("fridgeId") long fridgeId,
                                                      Authentication authentication) {
        if (!fridgeService.isUserInFridge(authentication.getName(), fridgeId)
                && !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return fridgeService.getFridgeByFridgeId(fridgeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Adds a product to the fridge of a group
     *
     * @param request the request containing the group id and product id
     * @param authentication a user authentication object for validation of user authorization
     * @return success if the product was added, bad request if the product was already in the fridge, or not found if the group or product doesn't exist
     */
    @PostMapping("/group/product")
    public ResponseEntity<Product> addProductToFridge(@RequestBody FridgeProductRequest request,
                                                      Authentication authentication) {

        Optional<Fridge> fridge = fridgeService.getFridgeByGroupId(request.groupId());

        if (fridge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Authority.ADMIN.name()))){
            if (!fridgeService.isUserInFridge(authentication.getName(), fridge.get().getFridgeId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        try {
            return fridgeService.addProductToFridge(request).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates a product in a fridge
     * @param request the request containing the group id and product id
     * @param authentication a user authentication object for validation of user authorization
     * @return success if the product was added, bad request if the product was already in the fridge,
     * or not found if the group or product doesn't exist
     */
    @PutMapping("/group/product")
    public ResponseEntity<FridgeProductAsso> updateProductInFridge(@RequestBody FridgeProductRequest request,
                                                                   Authentication authentication) {
        Optional<Fridge> fridge = fridgeService.getFridgeByGroupId(request.groupId());

        if (fridge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Authority.ADMIN.name()))){
            if (!fridgeService.isUserInFridge(authentication.getName(), fridge.get().getFridgeId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if(groupService.getUserGroupAssoAuthority(authentication.getName(), request.groupId())
                    .equalsIgnoreCase("RESTRICTED"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return fridgeService.updateProductInFridge(request).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    /**
     * Deletes an amount of a product from a fridge
     * @param fridgeProductId the id of the fridge product to delete
     * @param amountStr      the amount to delete
     * @param authentication the authentication of the user
     * @return 200 if the amount was deleted, 404 if the fridge product doesn't exist, 403 if the user is not in the group
     */
    @DeleteMapping("/group/delete/product/{fridgeProductId}/{amount}")
    public ResponseEntity<?> deleteAmountFridgeProduct(@PathVariable("fridgeProductId") long fridgeProductId,
                                                       @PathVariable("amount") String amountStr, Authentication authentication) {


        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Authority.ADMIN.name()))){
            if (!fridgeService.isUserInGroupWithFridgeProduct(authentication.getName(), fridgeProductId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if(groupService.getUserGroupAssoAuthority(authentication.getName(),
                    fridgeService.getGroupIdFromFridgeProuctId(fridgeProductId))
                    .equalsIgnoreCase("RESTRICTED")
                   )
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            double amount = Double.parseDouble(amountStr);

            if (amount < 0.0) {
                return ResponseEntity.badRequest().body("Amount must be greater than or equal to 0.");
            }

            return ResponseEntity.ok(fridgeService.deleteAmountFromFridge(fridgeProductId, amount));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid amount format. Please provide a valid number.");
        }
    }



    /**
     * Deletes a product from the fridge
     * @param fridgeProductId the id of the fridge product association
     * @param authentication the authentication of the user
     * @return success if the product was deleted, bad request if the product wasn't found
     * , or forbidden if the user is not in the group
     */
    @DeleteMapping("/delete/product/{fridgeProductId}")
    public ResponseEntity<String> removeProductFromFridge(@PathVariable("fridgeProductId") long fridgeProductId,
                                                          Authentication authentication) {

        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Authority.ADMIN.name()))){
            if (!fridgeService.isUserInGroupWithFridgeProduct(authentication.getName(), fridgeProductId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if(groupService.getUserGroupAssoAuthority(authentication.getName(),
                            fridgeService.getGroupIdFromFridgeProuctId(fridgeProductId))
                    .equalsIgnoreCase("RESTRICTED")
            )
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            return (fridgeService.removeProductFromFridge(fridgeProductId)) ?
                    ResponseEntity.ok("Product removed from fridge")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in the fridge");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    /**
     * Deletes a product from the fridge and creates a waste object from it.
     *
     * @param fridgeProductId The id of the fridge product association to be deleted
     * @param authentication The authentication of the user
     * @return A ResponseEntity with status code 200 if successful,
     * or status code 404 if the specified fridge product association was not found.
     * or status code 403 if the user is not in the group
     */
    @DeleteMapping("/waste/product/{fridgeProductId}")
    public ResponseEntity<?> wasteProductFromFridge(@PathVariable("fridgeProductId") long fridgeProductId,
                                                    Authentication authentication){
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Authority.ADMIN.name()))){
            if (!fridgeService.isUserInGroupWithFridgeProduct(authentication.getName(), fridgeProductId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if(groupService.getUserGroupAssoAuthority(authentication.getName(),
                            fridgeService.getGroupIdFromFridgeProuctId(fridgeProductId))
                    .equalsIgnoreCase("RESTRICTED")
            )
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return fridgeService.wasteProductFromFridge(fridgeProductId)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
