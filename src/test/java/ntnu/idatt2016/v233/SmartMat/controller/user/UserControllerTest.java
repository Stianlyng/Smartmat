package ntnu.idatt2016.v233.SmartMat.controller.user;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.product.AllergyRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.user.RegisterUserRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.user.UpdateUserRequest;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserController userController;


    private UpdateUserRequest updateUser;

    private Authentication authentication;

    @BeforeEach
    public void setup() {
        updateUser = new UpdateUserRequest(
                "John", "Doe", "johndoe@example.com", "newPassword123",
                Date.valueOf("1980-01-01"), List.of("Peanut", "Lactose")
        );
        authentication = mock(Authentication.class);
    }

    @Test
    void registerUser_validRequest_shouldReturnCreatedUser() {
        // Arrange
        RegisterUserRequest userDto = new RegisterUserRequest(
                "kari123",
                "sjokoladekake",
                "kari.nordman@gmail.com",
                "kari",
                "nordmann",
                Date.valueOf(LocalDate.of(2001, 8, 10))
        );

        // Act
        ResponseEntity<RegisterUserRequest> response = restTemplate.postForEntity("/api/user/register", userDto, RegisterUserRequest.class);

        // Assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());

        assertEquals(userDto.username(), response.getBody().username());
        assertEquals(userDto.email(), response.getBody().email());
        assertEquals(userDto.firstName(), response.getBody().firstName());
        assertEquals(userDto.lastName(), response.getBody().lastName());


        Optional<User> userOptional = userRepository.findByUsername("kari123");
        assertTrue(userOptional.isPresent());

    }


    @Test
    public void testUpdateUserWithValidUsernameAndRequestBodyReturnsOk() {
        // Arrange
        String username = "johndoe";

        User user = new User();
        user.setUsername(username);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("oldPassword123");
        user.setAllergies(new ArrayList<>());
        user.setDateOfBirth(Date.valueOf("1980-01-01"));
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserFromUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updateUser.password())).thenReturn("encodedPassword");

        when(userService.updateUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.updateUser(username, updateUser, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("johndoe@example.com", response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
        assertEquals(Date.valueOf("1980-01-01"), response.getBody().getDateOfBirth());

        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void getUser_validUsername_shouldReturnUser() {
        // Arrange
        String username = "johndoe";
        User user = new User();
        user.setUsername(username);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("oldPassword123");
        user.setAllergies(new ArrayList<>());
        user.setDateOfBirth(Date.valueOf("1980-01-01"));
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserFromUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.getUser(username, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(username, response.getBody().getUsername());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("johndoe@example.com", response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
        assertEquals(Date.valueOf("1980-01-01"), response.getBody().getDateOfBirth());
    }

    @Test
    void getUser_invalidUsername_shouldReturnNotFound() {
        // Arrange
        String username = "nonexistent";
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserFromUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.getUser(username, authentication);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addAllergyToUser_validRequest_shouldReturnOk() {
        // Arrange
        String username = "johndoe";
        String allergyName = "Peanut";
        AllergyRequest allergyRequest = new AllergyRequest(username, allergyName);
        when(authentication.getName()).thenReturn(username);
        when(userService.addAllergyToUser(username, allergyName)).thenReturn(ResponseEntity.ok("Allergy added"));

        // Act
        ResponseEntity<String> response = userController.addAllergyToUser(allergyRequest, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Allergy added", response.getBody());
    }

    @Test
    void deleteAllergyFromUser_validRequest_shouldReturnOk() {
        // Arrange
        String username = "johndoe";
        String allergyName = "Peanut";
        AllergyRequest allergyRequest = new AllergyRequest(username, allergyName);
        when(authentication.getName()).thenReturn(username);
        when(userService.removeAllergyFromUser(username, allergyName)).thenReturn(ResponseEntity.ok("Allergy removed"));

        // Act
        ResponseEntity<String> response = userController.deleteAllergyFromUser(allergyRequest, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Allergy removed", response.getBody());
    }

    @Test
    void addAllergyToUser_invalidRequest_shouldReturnForbidden() {
        // Arrange
        String username = "johndoe";
        String allergyName = "Peanut";
        AllergyRequest allergyRequest = new AllergyRequest(username, allergyName);
        when(authentication.getName()).thenReturn("differentUser");
        when(authentication.getAuthorities()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> response = userController.addAllergyToUser(allergyRequest, authentication);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteAllergyFromUser_invalidRequest_shouldReturnForbidden() {
        // Arrange
        String username = "johndoe";
        String allergyName = "Peanut";
        AllergyRequest allergyRequest = new AllergyRequest(username, allergyName);
        when(authentication.getName()).thenReturn("differentUser");
        when(authentication.getAuthorities()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> response = userController.deleteAllergyFromUser(allergyRequest, authentication);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateUser_invalidUsername_shouldReturnNotFound() {
        // Arrange
        String username = "nonexistent";
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserFromUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.updateUser(username, updateUser, authentication);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUser_invalidAuthentication_shouldReturnForbidden() {
        // Arrange
        String username = "johndoe";
        when(authentication.getName()).thenReturn("differentUser");

        // Act
        ResponseEntity<User> response = userController.updateUser(username, updateUser, authentication);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getUser_unauthorizedAccess_shouldReturnForbidden() {
        // Arrange
        String username = "johndoe";
        when(authentication.getName()).thenReturn("differentUser");
        when(authentication.getAuthorities()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<User> response = userController.getUser(username, authentication);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getUser_adminAccess_shouldReturnUser() {
        // Arrange
        String username = "johndoe";
        User user = new User();
        user.setUsername(username);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("oldPassword123");
        user.setAllergies(new ArrayList<>());
        user.setDateOfBirth(Date.valueOf("1980-01-01"));
        when(authentication.getName()).thenReturn("admin");
        doReturn(List.of(new SimpleGrantedAuthority(Authority.ADMIN.name())))
                .when(authentication).getAuthorities();
        when(userService.getUserFromUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.getUser(username, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(username, response.getBody().getUsername());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("johndoe@example.com", response.getBody().getEmail());
        assertNull(response.getBody().getPassword());
        assertEquals(Date.valueOf("1980-01-01"), response.getBody().getDateOfBirth());
    }
}