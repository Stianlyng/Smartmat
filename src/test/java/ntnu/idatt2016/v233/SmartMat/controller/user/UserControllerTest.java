package ntnu.idatt2016.v233.SmartMat.controller.user;

import ntnu.idatt2016.v233.SmartMat.dto.request.RegisterUserRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.UpdateUserRequest;
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
}