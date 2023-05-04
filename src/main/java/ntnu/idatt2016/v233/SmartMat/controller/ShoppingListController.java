package ntnu.idatt2016.v233.SmartMat.controller;

import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.service.ShoppingListService;

/**
 * Controller for the shopping list
 * 
 * @author Stian Lyng
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/shoppinglist")
public class ShoppingListController {

    ShoppingListService shoppingListService;

    ProductService productService;


    UserService userService;



    /**
     * Gets a shopping list by its ID
     *
     * @param id the shopping list ID
     * @return the shopping list, or an error if the ID is invalid
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable("id") long id, Authentication auth) {
        shoppingListService.isUserInShoppinglist(id, auth.getName());

        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListById(id);
        return shoppingList.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    /**
     * Gets a shopping list by its group ID
     * 
     * @param id the request containing the group ID
     * @return the shopping list, or an error if the ID is invalid
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ShoppingList> getAllShoppingListsByGroupId(@PathVariable("groupId") long id) {
        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListByGroupId(id);
        return shoppingList.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Adds a product to a shopping list
     * @param shoppingListId the shopping list ID
     * @param ean           the product EAN
     * @param auth         the authentication object
     * @return the shopping list with the added product, or an error if the shopping list ID or EAN is invalid
     */
    @PostMapping("/addProduct/{shoppingListId}/{ean}")
    public ResponseEntity<?> addItemToShoppingList(@PathVariable("shoppingListId") long shoppingListId,
                                                              @PathVariable("ean") String ean, Authentication auth){

        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListById(shoppingListId);

        if(shoppingList.isEmpty())
            return ResponseEntity.badRequest().body("shoppinglist not found");

        if(productService.getProductById(Long.parseLong(ean)).isEmpty())
            return ResponseEntity.badRequest().body("product not found");

        Optional<User> user = userService.getUserFromUsername(auth.getName());


        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        if(user.get().getGroup().stream().noneMatch(userGroupAsso ->
                userGroupAsso.getGroup().getGroupId() == shoppingList.get().getGroup().getGroupId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        Optional<Product> product = shoppingList.get().getProducts().stream().filter(p ->
                p.getEan() == (Long.parseLong(ean))).findFirst();

        if(product.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        System.out.println("Adding product to shopping list : " + shoppingListId + " - " + ean);

        Optional<ShoppingList> returnval = shoppingListService.addProductToShoppingList(Long.parseLong(ean) ,
                shoppingListId
                );



        return returnval.map(list -> ResponseEntity.status(HttpStatus.OK).body(list.getProducts()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());


    }

    /**
     * Removes a product from a shopping list
     *
     * @param shoppingListId the shopping list ID
     * @param ean           the product EAN
     * @return the shopping list with the removed product, or an error if the shopping list ID or EAN is invalid
     */
    @DeleteMapping("/removeProduct/{shoppingListId}/{ean}")
    public ResponseEntity<ShoppingList> removeProductFromShoppingList(@PathVariable("shoppingListId") String shoppingListId,
                                                                      @PathVariable("ean") String ean) {
        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListById(Long.parseLong(shoppingListId));
        if(shoppingList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Optional<Product> product = shoppingList.get().getProducts().stream().filter(p ->
                p.getEan() == (Long.parseLong(ean))).findFirst();

        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        System.out.println("Removing product from shopping list : " + shoppingListId + " - " + ean);

        Optional<ShoppingList> returnVal = shoppingListService
                .removeProductFromShoppingList(Long.parseLong(ean), Long.parseLong(shoppingListId));

        return returnVal.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
