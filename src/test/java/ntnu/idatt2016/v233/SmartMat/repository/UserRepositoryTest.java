package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this annotation will setup an in-memory database for testing
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .authority(Authority.USER)
                .build();
        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findByUsername(user.getUsername());
        assertTrue(retrievedUser.isPresent());
        assertEquals(user.getUsername(), retrievedUser.get().getUsername());
        assertEquals(user.getEmail(), retrievedUser.get().getEmail());
        assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
        assertEquals(user.getLastName(), retrievedUser.get().getLastName());
        assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
        assertEquals(user.getAuthorities(), retrievedUser.get().getAuthorities());

        userRepository.delete(user);
        assertFalse(userRepository.findByUsername(user.getUsername()).isPresent());
    }


    @Test
    public void findAllTest() {
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("password1");
        user1.setEnabled(true);
        user1.setEmail("testuser1@example.com");
        user1.setFirstName("Test1");
        user1.setLastName("User1");
        userRepository.save(user1);
        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setPassword("password2");
        user2.setEnabled(true);
        user2.setEmail("testuser2@example.com");
        user2.setFirstName("Test2");
        user2.setLastName("User2");
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertEquals(user1.getUsername(), users.get(0).getUsername());
        assertEquals(user2.getUsername(), users.get(1).getUsername());
    }

    @Test
    public void deleteTest() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEnabled(true);
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        userRepository.save(user);
        userRepository.delete(user);
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void testOverwriteUser() {
        // Create a new user and save it to the database
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .authority(Authority.USER)
                .build();
        userRepository.save(user);

        // Modify the user's details and save it again
        User modifiedUser = User.builder()
                .username("testuser")
                .password("newpassword")
                .enabled(false)
                .email("newemail@example.com")
                .firstName("New")
                .lastName("Name")
                .dateOfBirth(Date.valueOf("1995-01-01"))
                .authority(Authority.ADMIN)
                .build();
        userRepository.save(modifiedUser);

        // Verify that the modified user has replaced the original user
        Optional<User> retrievedUser = userRepository.findByUsername(user.getUsername());
        assertTrue(retrievedUser.isPresent());
        assertEquals(modifiedUser.getUsername(), retrievedUser.get().getUsername());
        assertEquals(modifiedUser.getEmail(), retrievedUser.get().getEmail());
        assertEquals(modifiedUser.getFirstName(), retrievedUser.get().getFirstName());
        assertEquals(modifiedUser.getLastName(), retrievedUser.get().getLastName());
        assertEquals(modifiedUser.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
        assertEquals(modifiedUser.getAuthorities(), retrievedUser.get().getAuthorities());

        // Clean up by deleting the modified user
        userRepository.delete(modifiedUser);
        assertFalse(userRepository.findByUsername(user.getUsername()).isPresent());
    }

}
