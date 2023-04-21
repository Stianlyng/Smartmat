package ntnu.idatt2016.v233.SmartMat.controller.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGroupByName() {
        Group group = new Group();
        group.setGroupName("testGroup");

        when(groupService.getGroupByName("testGroup")).thenReturn(Optional.of(group));

        GroupController controller = new GroupController(groupService);

        ResponseEntity<Group> response = controller.getGroupByName("testGroup");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group, response.getBody());
    }

    @Test
    void getGroupById() {
        Group group = new Group();
        group.setGroupId(1L);

        when(groupService.getGroupById(1L)).thenReturn(Optional.of(group));

        GroupController controller = new GroupController(groupService);

        ResponseEntity<Group> response = controller.getGroupById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group, response.getBody());
    }

    @Test
    void createGroup() {
        Group group = new Group();
        group.setGroupId(1L);
        group.setGroupName("testGroup");

        when(groupService.createGroup(group)).thenReturn(group);

        GroupController controller = new GroupController(groupService);

        ResponseEntity<Group> response = controller.createGroup(group);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group, response.getBody());
    }
}
