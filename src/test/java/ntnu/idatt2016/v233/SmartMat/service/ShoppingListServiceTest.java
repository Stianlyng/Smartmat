package ntnu.idatt2016.v233.SmartMat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.dto.request.ShoppingListRequest;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.repository.ShoppingListRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingListServiceTest {

    @InjectMocks
    private ShoppingListService shoppingListService;

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShoppingList() {
        ShoppingListRequest shoppingListRequest = new ShoppingListRequest(1L);
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setGroupID(1L);

        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);

        ShoppingList result = shoppingListService.createShoppingList(shoppingListRequest);

        assertEquals(1L, result.getGroupID());
    }

    @Test
    void testGetShoppingListById() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID(1L);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        Optional<ShoppingList> result = shoppingListService.getShoppingListById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getShoppingListID());
    }

    @Test
    void testGetShoppingListByGroupId() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setGroupID(1L);

        when(shoppingListRepository.getByGroupID(1L)).thenReturn(Optional.of(shoppingList));

        Optional<ShoppingList> result = shoppingListService.getShoppingListByGroupId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getGroupID());
    }

    @Test
    void testGetAllShoppingLists() {
        ShoppingList shoppingList1 = new ShoppingList();
        ShoppingList shoppingList2 = new ShoppingList();

        when(shoppingListRepository.findAll()).thenReturn(Arrays.asList(shoppingList1, shoppingList2));

        List<ShoppingList> result = shoppingListService.getAllShoppingLists();

        assertEquals(2, result.size());
    }

    @Test
    void testDeleteShoppingListById() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID(1L);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        shoppingListService.deleteShoppingListById(1L);

        verify(shoppingListRepository, times(1)).deleteById(1L);
    }
}
