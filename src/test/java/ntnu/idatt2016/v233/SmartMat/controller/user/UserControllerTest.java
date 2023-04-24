package ntnu.idatt2016.v233.SmartMat.controller.user;

import ntnu.idatt2016.v233.SmartMat.dto.request.RegisterUserRequest;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

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
}