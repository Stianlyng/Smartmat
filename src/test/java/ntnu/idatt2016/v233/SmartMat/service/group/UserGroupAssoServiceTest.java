package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserGroupAssoServiceTest {

    @Mock
    private UserGroupAssoRepository userGroupAssoRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserGroupAssoService userGroupAssoService;

    private User user;
    private Group group;
    private UserGroupAsso userGroupAsso;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");

        group = new Group();
        group.setGroupId(1L);

        userGroupAsso = new UserGroupAsso();
        userGroupAsso.setUser(user);
        userGroupAsso.setGroup(group);
        userGroupAsso.setPrimaryGroup(true);
        userGroupAsso.setGroupAuthority("ADMIN");
        userGroupAsso.setId(UserGroupId.builder()
                .groupId(group.getGroupId())
                .username(user.getUsername())
                .build());
    }

    @Test
    public void testSave() {
        userGroupAssoService.save(user, group, "ADMIN");

        verify(userGroupAssoRepository, times(1)).save(any(userGroupAsso.getClass()));
        verify(userRepository, times(1)).save(user);
        verify(groupRepository, times(1)).save(group);
    }
}
