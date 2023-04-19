package ntnu.idatt2016.v233.SmartMat.entity.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetAuthorities() {
        User user = User.builder()
                .authority(Authority.USER)
                .build();

        assertEquals(Collections.singletonList(new SimpleGrantedAuthority(Authority.USER.name())), user.getAuthorities());
    }

    @Test
    void testGetPassword() {
        User user = User.builder()
                .password("password123")
                .build();

        assertEquals("password123", user.getPassword());
    }

    @Test
    void testGetUsername() {
        User user = User.builder()
                .username("johndoe")
                .build();

        assertEquals("johndoe", user.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        User user = new User();

        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        User user = new User();

        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        User user = new User();

        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        User user = User.builder()
                .enabled(true)
                .build();

        assertTrue(user.isEnabled());
    }
}
