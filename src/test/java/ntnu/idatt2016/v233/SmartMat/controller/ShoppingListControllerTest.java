package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.request.ShoppingListRequest;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingListControllerTest {

    @InjectMocks
    private ShoppingListController shoppingListController;

    @Mock
    private ShoppingListService shoppingListService;

    private ShoppingList shoppingList;

    @BeforeEach
    public void setUp() {
        shoppingList = new ShoppingList();
        // Set properties for the shoppingList object
    }


    @Test
    public void getShoppingListById_found() {
        long id = 1;
        when(shoppingListService.getShoppingListById(id)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> response = shoppingListController.getShoppingListById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingList, response.getBody());
    }

    @Test
    public void getShoppingListById_notFound() {
        long id = 1;
        when(shoppingListService.getShoppingListById(id)).thenReturn(Optional.empty());

        ResponseEntity<ShoppingList> response = shoppingListController.getShoppingListById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllShoppingListsByGroupId_found() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingList, response.getBody());
    }

    @Test
    public void getAllShoppingListsByGroupId_notFound() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.empty());

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
