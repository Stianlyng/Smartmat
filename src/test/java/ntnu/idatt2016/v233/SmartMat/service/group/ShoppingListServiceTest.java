package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.group.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.group.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.repository.group.ShoppingListRepository;

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
        Group group = new Group();
        shoppingList.setGroup(group);

        when(shoppingListRepository.getByGroupGroupId(group.getGroupId())).thenReturn(Optional.of(shoppingList));

        Optional<ShoppingList> result = shoppingListService.getShoppingListByGroupId(group.getGroupId());

        assertTrue(result.isPresent());
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

    @Test
    void isUserInGroupWithShoppinglist(){
        ShoppingList shoppingList = new ShoppingList();
        Group group = new Group();
        shoppingList.setGroup(group);

        User user = User.builder()
                .username("test")
                .password("test")
                .build();

        group.addUser(UserGroupAsso.builder()
                        .user(user)
                        .group(group)
                .build());


        when(shoppingListRepository.findAllByGroupUserUserUsername(user.getUsername()))
                .thenReturn(List.of(shoppingList));

        boolean result = shoppingListService.isUserInShoppinglist(
                shoppingList.getShoppingListID(), user.getUsername());

        assertTrue(result);

        verify(shoppingListRepository, times(1))
                .findAllByGroupUserUserUsername(user.getUsername());
    }
}
