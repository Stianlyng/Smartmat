package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.ShoppingListRequest;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.ShoppingListService;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingListControllerTest {

    @InjectMocks
    private ShoppingListController shoppingListController;

    @Mock
    private ShoppingListService shoppingListService;

    @Mock
    private GroupService groupService;

    private ShoppingList shoppingList;

    private final Authentication regularUser = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(Authority.USER.name()));
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "test";
        }
    };

    private Authentication adminUser = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(Authority.ADMIN.name()));
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "test";
        }
    };

    @BeforeEach
    public void setUp() {
        shoppingList = new ShoppingList();
        Group group = Group.builder()
                        .groupId(1)
                                .build();

        User user = User.builder()
                        .username(regularUser.getName())
                        .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                .user(user)
                .group(group)
                .groupAuthority("USER")
                .build();


        group.addUser(userGroupAsso);
        user.addGroup(userGroupAsso);

        shoppingList.setGroup(group);


    }


    @Test
    public void getShoppingListById_found() {
        long id = 1;
        when(shoppingListService.getShoppingListById(id)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> response = shoppingListController.getShoppingListById(id, adminUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingList, response.getBody());
    }

    @Test
    public void getShoppingListById_notFound() {
        long id = 1;
        when(shoppingListService.getShoppingListById(id)).thenReturn(Optional.empty());

        ResponseEntity<ShoppingList> response = shoppingListController.getShoppingListById(id, adminUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllShoppingListsByGroupId_found() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId, adminUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingList, response.getBody());
    }

    @Test
    public void getAllShoppingListsByGroupId_notFound() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.empty());

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId, adminUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllShoppingListsByGroupId_foundReg() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.of(shoppingList));
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), groupId)).thenReturn(true);

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId, regularUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingList, response.getBody());
    }

    @Test
    public void getAllShoppingListsByGroupId_notFoundReg() {
        long groupId = 1;
        when(shoppingListService.getShoppingListByGroupId(groupId)).thenReturn(Optional.empty());
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), groupId)).thenReturn(true);

        ResponseEntity<ShoppingList> response = shoppingListController.getAllShoppingListsByGroupId(groupId, regularUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
