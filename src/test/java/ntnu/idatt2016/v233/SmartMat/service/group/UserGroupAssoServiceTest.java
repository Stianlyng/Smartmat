package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGroupAssoServiceTest {

    @Mock
    UserGroupAssoRepository userGroupAssoRepository;

    @InjectMocks
    UserGroupAssoService userGroupAssoService;

    @Test
    void save() {

        UserGroupAsso tempAsso = new UserGroupAsso();

        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername("test");

        Group tempgroup = new Group();
        tempgroup.setGroupName("test");

        tempAsso.setId(UserGroupId.builder().username(user.getUsername())
                .groupId(tempgroup.getGroupId()).build());
        tempAsso.setGroup(tempgroup);
        tempAsso.setUser(user);
        tempAsso.setPrimaryGroup(true);

        tempgroup.addUser(tempAsso);
        user.addGroup(tempAsso);


        when(userGroupAssoRepository.save(tempAsso))
                .thenReturn(tempAsso);


        userGroupAssoService.save(user, tempgroup,"Admin");

        //make sure the correct assisiation is saved
        verify(userGroupAssoRepository, times(1)).save(tempAsso);


        assertEquals(user.getGroup().get(0), tempgroup.getUser().get(0));


    }
}