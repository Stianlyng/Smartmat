package ntnu.idatt2016.v233.SmartMat.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;

@DataJpaTest
public class ShoppingListRepositoryTest {
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Test
    public void testGetByGroupID() {
        ShoppingList shoppingList = ShoppingList.builder()
                .groupID(1L)
                .build();

        shoppingListRepository.save(shoppingList);

        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.getByGroupID(1L);

        assertTrue(shoppingListOptional.isPresent());

        ShoppingList tempshoppingList = shoppingListOptional.get();
        assertEquals(1L, tempshoppingList.getGroupID());
    }

    @Test
    public void testSaveShoppingList() {
        ShoppingList shoppingList = ShoppingList.builder()
                .groupID(1L)
                .build();

        shoppingListRepository.save(shoppingList);

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(1, shoppingLists.size());
    }

    @Test
    public void testDeleteShoppingList() {
        ShoppingList shoppingList = ShoppingList.builder()
                .groupID(1L)
                .build();

        shoppingListRepository.save(shoppingList);

        shoppingListRepository.deleteById(shoppingList.getShoppingListID());

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(0, shoppingLists.size());
    }

    @Test
    public void testFindAll() {
        ShoppingList shoppingList1 = ShoppingList.builder()
                .groupID(1L)
                .build();

        ShoppingList shoppingList2 = ShoppingList.builder()
                .groupID(2L)
                .build();

        shoppingListRepository.save(shoppingList1);
        shoppingListRepository.save(shoppingList2);

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(2, shoppingLists.size());
    }
}
