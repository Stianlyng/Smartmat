package ntnu.idatt2016.v233.SmartMat.controller.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.service.group.UserGroupAssoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserGroupAssoControllerTest {

    @InjectMocks
    private UserGroupAssoController userGroupAssoController;

    @Mock
    private UserGroupAssoService userGroupAssoService;

    private List<UserGroupAsso> userGroupAssoList;

    @BeforeEach
    public void setUp() {
        UserGroupAsso userGroupAsso1 = new UserGroupAsso();
        UserGroupAsso userGroupAsso2 = new UserGroupAsso();
        // Set properties for userGroupAsso1 and userGroupAsso2
        userGroupAssoList = Arrays.asList(userGroupAsso1, userGroupAsso2);
    }

    @Test
    public void getInformationByGroupId_found() {
        long groupId = 1L;
        when(userGroupAssoService.getInformationByGroupId(groupId)).thenReturn(Optional.of(userGroupAssoList));

        ResponseEntity<List<UserGroupAsso>> response = userGroupAssoController.getInformationByGroupId(groupId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userGroupAssoList, response.getBody());
    }

    @Test
    public void getInformationByGroupId_notFound() {
        long groupId = 1L;
        when(userGroupAssoService.getInformationByGroupId(groupId)).thenReturn(Optional.empty());

        ResponseEntity<List<UserGroupAsso>> response = userGroupAssoController.getInformationByGroupId(groupId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
