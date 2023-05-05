package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * JpaUserDetailsService is a class that implements the UserDetailsService interface.
 * @author Birk
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private UserService userService;

    /**
     * gets user from username out of database
     * @param username username of user
     * @return user
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserFromUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user found with username = " + username)
                );
    }

}
