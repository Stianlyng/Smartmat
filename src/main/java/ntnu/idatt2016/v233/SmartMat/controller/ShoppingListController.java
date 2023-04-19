package ntnu.idatt2016.v233.SmartMat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.ShoppingListRequest;
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

    @Autowired
    ShoppingListService shoppingListService;
    
    /**
     * Creates a shopping list
     *
     * @param request the request containing the group ID
     * @return the created shopping list, or an error if the group ID is invalid
     */
    @PostMapping("/add")
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingListRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingListService.createShoppingList(request));
    }

    /**
     * Gets a shopping list by its ID
     *
     * @param request the request containing the shopping list ID
     * @return the shopping list, or an error if the ID is invalid
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@RequestBody ShoppingListRequest request) {
        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListById(request.getGroupID());
        return shoppingList.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    /**
     * Gets a shopping list by its group ID
     * 
     * @param request the request containing the group ID
     * @return the shopping list, or an error if the ID is invalid
     */
    @GetMapping("/group/{id}")
    public ResponseEntity<ShoppingList> getAllShoppingListsByGroupId(@RequestBody ShoppingListRequest request) {
        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListByGroupId(request.getGroupID());
        return shoppingList.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
