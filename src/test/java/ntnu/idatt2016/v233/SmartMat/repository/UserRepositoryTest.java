package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // this annotation will setup an in-memory database for testing
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .authority(Authority.USER)
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();
        entityManager.persist(user);
    }

    @Test
    void testSaveUser() {
        User user = User.builder()
                .username("testuserTESTUSER")
                .password("passwordTEST")
                .enabled(true)
                .email("testuser@example.no")
                .firstName("TestUSERNAME")
                .lastName("UserTEST")
                .authority(Authority.USER)
                .dateOfBirth(Date.valueOf("1989-01-01"))
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
        // Modify the user's details and save it again
        User modifiedUser = User.builder()
                .username("testuser")
                .password("newpassword")
                .enabled(false)
                .email("newemail@example.com")
                .firstName("New")
                .lastName("Name")
                .authority(Authority.USER)
                .dateOfBirth(Date.valueOf("1995-01-01"))
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


    @Test
    void testFindByUsername() {
        Optional<User> tempuser = userRepository.findByUsername("testuser");

        assertTrue(tempuser.isPresent());
        assertEquals("testuser", tempuser.get().getUsername());
        assertEquals("password", tempuser.get().getPassword());
        assertTrue(tempuser.get().isEnabled());

    }


    @Autowired
    AllergyRepository allergyRepository;

    @Test
    void shouldGetAllergiesByUser(){
        Allergy allergy = new Allergy();
        allergy.setName("testallergy");



        User tempuser = User.builder()
                .username("test2user")
                .password("password")
                .enabled(true)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .allergies(List.of(allergy))
                .build();


        allergyRepository.save(allergy);

        userRepository.save(tempuser);

        Optional<User> tempuser2 = userRepository.findByUsername("test2user");


        assertTrue(tempuser2.isPresent());


        assertNotNull(tempuser2.get().getAllergies());

        assertEquals(1, tempuser2.get().getAllergies().size());

        assertEquals(1, tempuser2.get().getAllergies().size());
        assertEquals("testallergy", tempuser2.get().getAllergies().get(0).getName());

        tempuser2.get().getAllergies().forEach(allergy1 -> {
            assertNull(allergy1.getUsers());
        });

    }


}
