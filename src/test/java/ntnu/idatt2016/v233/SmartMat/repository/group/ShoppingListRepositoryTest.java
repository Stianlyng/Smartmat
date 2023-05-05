package ntnu.idatt2016.v233.SmartMat.repository.group;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.ShoppingListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ntnu.idatt2016.v233.SmartMat.entity.group.ShoppingList;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class ShoppingListRepositoryTest {
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testGetByGroupID() {
        Group group = Group.builder()
                .build();
        System.out.println(group.getGroupId());
        group = entityManager.persistFlushFind(group);

        ShoppingList shoppingList = ShoppingList.builder()
                .group(group)
                .build();

        shoppingListRepository.save(shoppingList);

        System.out.println(shoppingListRepository.findById(shoppingList.getShoppingListID()));
        System.out.println(group.getGroupId());

        Optional<ShoppingList> shoppingListOptional = shoppingListRepository
                .getByGroupGroupId(group.getGroupId());

        assertTrue(shoppingListOptional.isPresent());

        ShoppingList tempshoppingList = shoppingListOptional.get();
        assertEquals(group.getGroupId(), tempshoppingList.getGroup().getGroupId());
    }

    @Test
    public void testSaveShoppingList() {
        Group group = Group.builder()
                .build();
        ShoppingList shoppingList = ShoppingList.builder()
                .group(group)
                .build();

        shoppingListRepository.save(shoppingList);

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(1, shoppingLists.size());
    }

    @Test
    public void testDeleteShoppingList() {
        Group group = Group.builder()
                .build();
        ShoppingList shoppingList = ShoppingList.builder()
                .group(group)
                .build();

        shoppingListRepository.save(shoppingList);

        shoppingListRepository.deleteById(shoppingList.getShoppingListID());

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(0, shoppingLists.size());
    }

    @Test
    public void testFindAll() {
        Group group = Group.builder()
                .build();
        ShoppingList shoppingList1 = ShoppingList.builder()
                .group(group)
                .build();

        Group group2 = Group.builder()
                .build();
        ShoppingList shoppingList2 = ShoppingList.builder()
                .group(group2)
                .build();

        shoppingListRepository.save(shoppingList1);
        shoppingListRepository.save(shoppingList2);

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        assertEquals(2, shoppingLists.size());
    }

    @Test
    void getbyusername(){
        Group group = Group.builder()
                .build();
        ShoppingList shoppingList1 = ShoppingList.builder()
                .group(group)
                .build();

        Group group2 = Group.builder()
                .build();
        ShoppingList shoppingList2 = ShoppingList.builder()
                .group(group2)
                .build();

        User user = User.builder()
                .username("username")
                .password("password")
                .build();

        entityManager.persist(user);
        entityManager.persist(group);

        group.addUser(UserGroupAsso.builder()
                        .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                        .group(group)
                        .user(user)
                .build());



        shoppingListRepository.save(shoppingList1);
        shoppingListRepository.save(shoppingList2);

        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();

        assertEquals(2, shoppingLists.size());

        List<ShoppingList> shoppingListsByUsername = shoppingListRepository
                .findAllByGroupUserUserUsername(user.getUsername());

        assertEquals(1, shoppingListsByUsername.size());

        ShoppingList tempShoppingList = shoppingListsByUsername.get(0);

        assertEquals(group.getGroupId(), tempShoppingList.getGroup().getGroupId());



    }
}
