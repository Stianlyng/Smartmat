package ntnu.idatt2016.v233.SmartMat.service.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.util.GroupUtil;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGroupByName() {
        String groupName = "Test Group";
        Group group = new Group();
        group.setGroupName(groupName);

        when(groupRepository.findByGroupName(groupName)).thenReturn(Optional.of(group));

        Optional<Group> result = groupService.getGroupByName(groupName);

        assertTrue(result.isPresent());
        assertEquals(groupName, result.get().getGroupName());
    }

    @Test
    void testGetGroupById() {
        long groupId = 1L;
        Group group = new Group();
        group.setGroupId(groupId);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        Optional<Group> result = groupService.getGroupById(groupId);

        assertTrue(result.isPresent());
        assertEquals(groupId, result.get().getGroupId());
    }

    @Test
    void testCreateGroup() {
        String groupName = "New Group";
        Group group = new Group();
        group.setGroupName(groupName);

        when(groupRepository.findByGroupName(groupName)).thenReturn(Optional.empty());
        when(groupRepository.findAllLinkCode()).thenReturn(Collections.emptyList());
        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Group createdGroup = groupService.createGroup(group);

        assertEquals(groupName, createdGroup.getGroupName());
        assertNotNull(createdGroup.getLinkCode());
    }

    @Test
    void testGetLevelByGroupId() {
        long groupId = 1L;
        long level = 3L;
        Group group = new Group();
        group.setGroupId(groupId);
        group.setLevel(level);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.getLevelByGroupId(groupId)).thenReturn(Optional.of(level));

        Optional<Long> result = groupService.getLevelByGroupId(groupId);

        assertTrue(result.isPresent());
        assertEquals(level, result.get());
    }
}
