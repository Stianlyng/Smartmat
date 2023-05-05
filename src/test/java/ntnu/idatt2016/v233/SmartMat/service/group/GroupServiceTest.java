package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.ShoppingListRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import ntnu.idatt2016.v233.SmartMat.util.GroupUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private FridgeRepository fridgeRepository;

    @Mock
    private UserGroupAssoRepository userGroupAssoRepository;

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGroupByName() {
        // Arrange
        String groupName = "Test Group";
        Group group = new Group();
        group.setGroupName(groupName);
        when(groupRepository.findByGroupName(groupName)).thenReturn(Optional.of(group));

        // Act
        Optional<Group> result = groupService.getGroupByName(groupName);

        // Assert
        assertEquals(group, result.get());
        verify(groupRepository).findByGroupName(groupName);
    }


    @Test
    void testCreateGroup() {
        // Arrange
        String groupName = "Test Group";
        Group group = new Group();
        group.setGroupName(groupName);
        when(groupRepository.findByGroupName(groupName)).thenReturn(Optional.empty());
        when(groupRepository.findAllLinkCode()).thenReturn(List.of());
        when(groupRepository.save(group)).thenReturn(group);

        // Act
        Group result = groupService.createGroup(group);

        // Assert
        assertEquals(group, result);
        assertNotNull(result.getLinkCode());
        
        verify(groupRepository).findByGroupName(groupName);
        verify(groupRepository).findAllLinkCode();
        verify(groupRepository).save(group);
    }


    @Test
    void testAddFridgeToGroup() {
        // Arrange
        long fridgeId = 1L;
        long groupId = 2L;
        Fridge fridge = new Fridge();
        Group group = new Group();
        when(fridgeRepository.findById(fridgeId)).thenReturn(Optional.of(fridge));
        when(groupRepository.findByGroupId(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);
        when(fridgeRepository.save(fridge)).thenReturn(fridge);

        // Act
        groupService.addFridgeToGroup(fridgeId, groupId);

        // Assert
        assertEquals(group, fridge.getGroup());
        assertEquals(fridge, group.getFridge());
        verify(fridgeRepository).findById(fridgeId);
        verify(groupRepository).findByGroupId(groupId);
        verify(groupRepository).save(group);
        verify(fridgeRepository).save(fridge);
    }

    @Test
    void isUserAssosiatedWithGroup(){
        // Arrange
        long userId = 1L;
        long groupId = 2L;
        Group group = new Group();
        group.setGroupId(groupId);
        User user = User.builder()
                .username("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(UserGroupId.builder()
                        .groupId(groupId)
                        .username("test")
                        .build())
                .user(user)
                .group(group)
                .build();

        group.addUser(userGroupAsso);
        user.addGroup(userGroupAsso);

        when(userGroupAssoRepository.findById(userGroupAsso.getId())).thenReturn(Optional.of(userGroupAsso));

        // Act
        boolean result = groupService.isUserAssociatedWithGroup(user.getUsername(), groupId);

        // Assert
        assertTrue(result);
        verify(userGroupAssoRepository).findById(userGroupAsso.getId());
    }

    @Test
    void openOrCloseGroup(){
        // Arrange
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);
        group.setOpen(false);
        when(groupRepository.findByGroupId(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        // Act
        groupService.OpenOrCloseGroup(groupId);

        // Assert
        assertTrue(group.getOpen());



        groupService.OpenOrCloseGroup(groupId);

        // Assert
        assertFalse(group.getOpen());
        verify(groupRepository, times(2)).findByGroupId(groupId);
        verify(groupRepository, times(2)).save(group);
    }

    @Test
    void removeUserFromGroup(){
        Group group = new Group();
        group.setGroupId(1L);
        group.setOpen(false);

        User user = User.builder()
                .username("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .user(user)
                .group(group)
                .id(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                        .build())
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);

        when(userGroupAssoRepository.findById(userGroupAsso.getId())).thenReturn(Optional.of(userGroupAsso));
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));


        assertTrue(groupService.removeUserFromGroup(userGroupAsso));

        verify(userGroupAssoRepository).delete(userGroupAsso);


    }

    @Test
    void setLevelByGroupId(){
            // Arrange
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);
        group.setLevel(1);
        group.setPoints(10);
        when(groupRepository.findByGroupId(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        // Act
        groupService.setLevelByGroupId(groupId, group.getPoints());

        // Assert
        assertEquals(0, group.getLevel());
        verify(groupRepository).findByGroupId(groupId);
        verify(groupRepository).save(group);
    }

    @Test
    void getProgressOfLevel(){
        // Arrange
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);
        group.setLevel(1);
        group.setPoints(100);
        when(groupRepository.findByGroupId(groupId)).thenReturn(Optional.of(group));

        // Act
        Optional<Integer> result = groupService.getProgressOfLevel(groupId);

        // Assert
        assertTrue(result.isPresent());

        assertEquals(GroupUtil.getProgressOfLevel(group.getPoints()), result.get());
        verify(groupRepository).findByGroupId(groupId);
    }

    @Test
    void getUserGroupAsso(){
        Group group = new Group();
        group.setGroupId(1L);
        group.setOpen(false);

        User user = User.builder()
                .username("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .user(user)
                .group(group)
                .id(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                        .build())
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);


        when(userGroupAssoRepository.findById(userGroupAsso.getId())).thenReturn(Optional.of(userGroupAsso));

        Optional<UserGroupAsso> result = groupService.getUserGroupAsso(userGroupAsso.getId().getUsername(),
                userGroupAsso.getId().getGroupId());

        assertTrue(result.isPresent());

        assertEquals(userGroupAsso, result.get());

        verify(userGroupAssoRepository).findById(userGroupAsso.getId());

    }

    @Test
    void getUserGroupAssoAuthority(){
        Group group = new Group();
        group.setGroupId(1L);
        group.setOpen(false);

        User user = User.builder()
                .username("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .user(user)
                .group(group)
                .groupAuthority("USER")
                .id(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                        .build())
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);

        when(userGroupAssoRepository.findById(userGroupAsso.getId())).thenReturn(Optional.of(userGroupAsso));

        String result = groupService.getUserGroupAssoAuthority(userGroupAsso.getId().getUsername(),
                userGroupAsso.getId().getGroupId());


        assertEquals("USER", result);

        verify(userGroupAssoRepository).findById(userGroupAsso.getId());

    }

    @Test
    void getGroupById(){
        // Arrange
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // Act
        Optional<Group> result = groupService.getGroupById(groupId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(group, result.get());
        verify(groupRepository).findById(groupId);
    }

    @Test
    void getGroupById_notFound(){
        // Arrange
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // Act
        Optional<Group> result = groupService.getGroupById(5L);

        // Assert
        assertTrue(result.isEmpty());
        verify(groupRepository).findById(5L);
    }

    @Test
    void updateUserGroupAsso(){
        Group group = new Group();
        group.setGroupId(1L);
        group.setOpen(false);

        User user = User.builder()
                .username("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .user(user)
                .group(group)
                .groupAuthority("USER")
                .id(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                        .build())
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);

        when(userGroupAssoRepository.findById(userGroupAsso.getId())).thenReturn(Optional.of(userGroupAsso));
        when(userGroupAssoRepository.save(any(UserGroupAsso.class))).thenReturn(UserGroupAsso.builder()
                .id(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                        .build())
                .groupAuthority("ADMIN")
                .group(group)
                .user(user)
                .build());

        UserGroupAsso result = groupService.updateUserGroupAsso(UserGroupAsso.builder()
                        .id(UserGroupId.builder()
                                .groupId(group.getGroupId())
                                .username(user.getUsername())
                                .build())
                        .groupAuthority("ADMIN")
                        .group(group)
                        .user(user)
                .build());


        assertEquals("ADMIN", result.getGroupAuthority());
        verify(userGroupAssoRepository).save(any(UserGroupAsso.class));
    }
}
