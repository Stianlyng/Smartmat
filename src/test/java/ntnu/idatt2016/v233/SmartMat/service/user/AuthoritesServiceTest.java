package ntnu.idatt2016.v233.SmartMat.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.AuthorityTable;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.user.AuthoritesRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthoritesServiceTest {

    @InjectMocks
    private AuthoritesService authoritesService;

    @Mock
    private AuthoritesRepository authoritesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthorityByAuth() {
        Authority authorityType = Authority.USER;
        AuthorityTable authorityTable = new AuthorityTable(authorityType);

        when(authoritesRepository.findById(authorityType)).thenReturn(Optional.of(authorityTable));

        AuthorityTable result = authoritesService.getAuthorityByAuth(authorityType);

        assertEquals(authorityType, result.getAuthority());
    }

    @Test
    void testGetAuthorityByAuthCreateNew() {
        Authority authorityType = Authority.ADMIN;

        when(authoritesRepository.findById(authorityType)).thenReturn(Optional.empty());
        when(authoritesRepository.save(any(AuthorityTable.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorityTable result = authoritesService.getAuthorityByAuth(authorityType);

        assertEquals(authorityType, result.getAuthority());
    }

    @Test
    void testAddUserToAuthority() {
        Authority authorityType = Authority.USER;
        AuthorityTable authorityTable = new AuthorityTable(authorityType);
        User user = new User();
        user.setUsername("testUser");

        authoritesService.addUserToAuthoriy(authorityTable, user);

        assertNotNull(authorityTable.getUsers());
        assertTrue(authorityTable.getUsers().contains(user));
    }
}
