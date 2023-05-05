package ntnu.idatt2016.v233.SmartMat.repository.user;

import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * repo for user entity
 * @author birk
 * @version 1.1
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * gets user from username out of database
     * @param username username of user
     * @return user
     */
    Optional<User> findByUsername(String username);


    /**
     * gets user from email out of database
     * @param email email of user
     * @return user
     */
    Optional<User> findByEmail(String email);
}
