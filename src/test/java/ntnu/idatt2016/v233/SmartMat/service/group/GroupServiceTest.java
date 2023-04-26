package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private FridgeRepository fridgeRepository;

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
}
