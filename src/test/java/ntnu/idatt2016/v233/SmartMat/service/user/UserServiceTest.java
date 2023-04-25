package ntnu.idatt2016.v233.SmartMat.service.user;


import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserFromUsername_returnsUser_whenUserExists() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserFromUsername(user.getUsername());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void getUserFromUsername_returnsEmptyOptional_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserFromUsername("nonexistentuser");

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }

    @Test
    public void getUsers_returnsListOfUsers_whenUsersExist() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(User.builder().username("test1").build());
        users.add(User.builder().username("test2").build());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void updateUser_returnsUser_whenUserExists() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUser_throwsUsernameNotFoundException_whenUserDoesNotExist() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(user));
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void getUserByEmail_returnsUser_whenUserExists() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserFromEmail(user.getEmail());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail(user.getEmail());

    }

    @Test
    void getPassword_returnsPassword_whenUserExists() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("password")
                .enabled(true)
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Act

        String result = userService.getPassword(user.getUsername());

        // Assert

        assertEquals(user.getPassword(), result);
        verify(userRepository, times(1)).findByUsername(user.getUsername());

    }

}