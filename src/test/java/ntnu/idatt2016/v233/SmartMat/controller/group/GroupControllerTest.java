package ntnu.idatt2016.v233.SmartMat.controller.group;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.ChangeAuthorityRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupConnectionRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupRequest;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupDetailsResponse;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

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

    private final Authentication adminUser = new Authentication() {
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


    User user;

    Group group;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username(regularUser.getName())
                .password("test")
                .authority(Authority.USER)
                .build();

        group = Group.builder()
                .groupId(1L)
                .groupName("test")
                .build();


        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                .user(user)
                .group(group)
                .build();

        group.addUser(userGroupAsso);
        user.addGroup(userGroupAsso);
    }



    @Test
    void testGetGroup() {

        when(groupService.getUserGroupAssoByUserName(regularUser.getName()))
                .thenReturn(group.getUser());
        ResponseEntity<List<UserGroupAsso>> userGroupAssos = groupController.getAllGroupsByUser(regularUser);

        assertSame(userGroupAssos.getStatusCode(), HttpStatus.OK);
        assertNotNull(userGroupAssos.getBody());
        assertTrue(userGroupAssos.getBody().contains(group.getUser().get(0)));

    }

    @Test
    void testGetGroupById(){
        when(groupService.getGroupById(group.getGroupId())).thenReturn(Optional.ofNullable(group));

        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);
        when(groupService.isUserAssociatedWithGroup(adminUser.getName(), group.getGroupId())).thenReturn(true);


        ResponseEntity<?> groupResponseEntity = groupController.getGroupById(group.getGroupId(), regularUser);
        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertEquals(group.getGroupId(), ((GroupDetailsResponse) groupResponseEntity.getBody()).getGroupId());

        groupResponseEntity = groupController.getGroupById(group.getGroupId(), adminUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertEquals(group.getGroupId(), ((GroupDetailsResponse) groupResponseEntity.getBody()).getGroupId());

    }

    @Test
    void testRemoveUserFromGroup(){

        User testUser = User.builder()
                .username("test2")
                .password("test")
                .authority(Authority.USER)
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(new UserGroupId(testUser.getUsername(), group.getGroupId()))
                .user(testUser)
                .group(group)
                .build();

        group.addUser(userGroupAsso);
        testUser.addGroup(userGroupAsso);

        when(groupService.getGroupById(group.getGroupId())).thenReturn(Optional.ofNullable(group));
        when(userService.getUserFromUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);

        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("ADMIN");

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> groupResponseEntity =
                groupController.removeUserFromGroup(group.getGroupId(), "test2", regularUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertEquals("User removed successfully.", groupResponseEntity.getBody());


        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("USER");

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        groupResponseEntity =
                groupController.removeUserFromGroup(group.getGroupId(), "test2", regularUser);

        assertSame(HttpStatus.FORBIDDEN, groupResponseEntity.getStatusCode());
        assertNull(groupResponseEntity.getBody());


        groupResponseEntity =
                groupController.removeUserFromGroup(group.getGroupId(), "test2", adminUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertEquals("User removed successfully.", groupResponseEntity.getBody());
    }

    @Test
    void shouldChangeAuthority(){
        when(groupService.getGroupById(group.getGroupId())).thenReturn(Optional.ofNullable(group));
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);
        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("ADMIN");

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        ChangeAuthorityRequest changeAuthorityRequest = new ChangeAuthorityRequest(user.getUsername(),
                group.getGroupId(), "USER");

        ResponseEntity<?> groupResponseEntity =
                groupController.changeAuthority(changeAuthorityRequest, regularUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertEquals("Authority changed successfully.", groupResponseEntity.getBody());

        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("USER");

        groupResponseEntity =
                groupController.changeAuthority(changeAuthorityRequest, regularUser);

        assertSame(HttpStatus.FORBIDDEN, groupResponseEntity.getStatusCode());
        assertNull(groupResponseEntity.getBody());

    }

    @Test
    void shouldChangeOpen(){
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);
        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("ADMIN");


        when(groupService.OpenOrCloseGroup(group.getGroupId())).thenReturn(Optional.of(true));

        ResponseEntity<?> groupResponseEntity =
                groupController.changeOpenValue(group.getGroupId(), regularUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());
        assertTrue((boolean) groupResponseEntity.getBody());

        when(groupService.getUserGroupAssoAuthority(regularUser.getName(), group.getGroupId())).thenReturn("USER");

        groupResponseEntity =
                groupController.changeOpenValue(group.getGroupId(), regularUser);

        assertSame(HttpStatus.FORBIDDEN, groupResponseEntity.getStatusCode());
        assertNull(groupResponseEntity.getBody());
    }

    @Test
    void shouldCreateGroup(){
        GroupRequest groupRequest = new GroupRequest("test group");

        when(groupService.createGroup(any(Group.class))).thenReturn(group);
        when(groupService.getGroupByName(groupRequest.groupName())).thenReturn(Optional.empty());
        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> result = groupController.createGroup(groupRequest, regularUser);

        assertSame(HttpStatus.OK, result.getStatusCode());

        assertNotNull(result.getBody());

        assertEquals(group.getGroupId(), ((GroupDetailsResponse) result.getBody()).getGroupId());

        verify(groupService, times(1)).createGroup(any(Group.class));
    }

    @Test
    void shouldNotCreateGroupWhenConflict(){
        GroupRequest groupRequest = new GroupRequest("test group");

        when(groupService.getGroupByName(groupRequest.groupName())).thenReturn(Optional.of(group));

        ResponseEntity<?> result = groupController.createGroup(groupRequest, regularUser);

        assertSame(HttpStatus.BAD_REQUEST, result.getStatusCode());

        assertNotNull(result.getBody());

        assertEquals("Group name already exists.", result.getBody());

        verify(groupService, times(0)).createGroup(any(Group.class));
    }


    @Test
    void markNewPrimaryTest(){

        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));


        when(groupService.updateUserGroupAsso(any(UserGroupAsso.class))).thenReturn(group.getUser().get(0));

        when(groupService.findPrimaryUserGroupAssoForUser(regularUser.getName()))
                .thenReturn(Optional.ofNullable(group.getUser().get(0)));

        when(groupService.getUserGroupAsso(regularUser.getName(), group.getGroupId()))
                .thenReturn(Optional.ofNullable(group.getUser().get(0)));

        group.getUser().get(0).setPrimaryGroup(false);

        ResponseEntity<?> result = groupController.markNewPrimaryGroup(group.getGroupId(), regularUser);

        assertSame(HttpStatus.OK, result.getStatusCode());

        assertNotNull(result.getBody());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", user.getUsername());
        responseBody.put("oldPrimaryGroupId", group.getGroupId());
        responseBody.put("newPrimaryGroupId", group.getGroupId());

        assertEquals(responseBody, result.getBody());

        assertEquals(group.getUser().get(0).getPrimaryGroup(), true);



        verify(groupService, times(2)).updateUserGroupAsso(any(UserGroupAsso.class));


        verify(userService, times(1)).updateUser(user);


    }

    @Test
    void markNewPrimaryTestNotAuthorizedWhenNotAssosiated(){

        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(false);


        ResponseEntity<?> result = groupController.markNewPrimaryGroup(group.getGroupId(), regularUser);

        assertSame(HttpStatus.FORBIDDEN, result.getStatusCode());

        assertNull(result.getBody());

        verify(groupService, times(0)).updateUserGroupAsso(any(UserGroupAsso.class));


        verify(userService, times(0)).updateUser(user);


    }

    @Test
    void addConnection(){
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(false);

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        when(groupService.getGroupByLinkCode("test2")).thenReturn(Optional.ofNullable(group));
        when(userService.updateUser(user)).thenReturn(user);
        group.setLinkCode("test2");


        GroupConnectionRequest groupConnectionRequest = new GroupConnectionRequest("test2");

        ResponseEntity<?> groupResponseEntity =
                groupController.addConnection(groupConnectionRequest, regularUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("groupId", group.getGroupId());
        responseBody.put("username", user.getUsername());

        assertEquals(responseBody, groupResponseEntity.getBody());

        verify(userService, times(1)).updateUser(user);

    }

    @Test
    void addConnectionNotAllowedWhenAlreadyConnected(){
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);

        when(userService.getUserFromUsername(regularUser.getName())).thenReturn(Optional.ofNullable(user));

        when(groupService.getGroupByLinkCode("test2")).thenReturn(Optional.ofNullable(group));
        group.setLinkCode("test2");


        GroupConnectionRequest groupConnectionRequest = new GroupConnectionRequest("test2");

        ResponseEntity<?> groupResponseEntity =
                groupController.addConnection(groupConnectionRequest, regularUser);

        assertSame(HttpStatus.BAD_REQUEST, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());


        assertEquals("User is already associated with the group.", groupResponseEntity.getBody());

        verify(userService, times(0)).updateUser(user);

    }

    @Test
    void getGroupLevel(){
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(true);



        when(groupService.getGroupById(group.getGroupId())).thenReturn(Optional.of(group));

        ResponseEntity<?> groupResponseEntity =
                groupController.getGroupLevel(group.getGroupId(), regularUser);

        assertSame(HttpStatus.OK, groupResponseEntity.getStatusCode());
        assertNotNull(groupResponseEntity.getBody());

        assertEquals(group.getLevel(), groupResponseEntity.getBody());

        verify(groupService, times(1)).getGroupById(group.getGroupId());

    }

    @Test
    void getGroupLevelNotAuthorized(){
        when(groupService.isUserAssociatedWithGroup(regularUser.getName(), group.getGroupId())).thenReturn(false);

        
        ResponseEntity<?> groupResponseEntity =
                groupController.getGroupLevel(group.getGroupId(), regularUser);

        assertSame(HttpStatus.FORBIDDEN, groupResponseEntity.getStatusCode());
        assertNull(groupResponseEntity.getBody());


        verify(groupService, times(0)).getGroupById(group.getGroupId());

    }
}