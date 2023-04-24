package ntnu.idatt2016.v233.SmartMat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
     * @param id the shopping list ID
     * @return the shopping list, or an error if the ID is invalid
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable("id") long id) {
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
    @GetMapping("/group/{id}")
    public ResponseEntity<ShoppingList> getAllShoppingListsByGroupId(@PathVariable("id") long id) {
        Optional<ShoppingList> shoppingList = shoppingListService.getShoppingListByGroupId(id);
        return shoppingList.map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
