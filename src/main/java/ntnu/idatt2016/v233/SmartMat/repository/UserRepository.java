package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * repo for user entity
 * uses mysql from server when ran
 * uses h2 in memory database when testing
 * @author birk
 * @version 1.0
 * @since 05.04.2023
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * gets user from username out of database
     * @param username username of user
     * @return user
     */
    Optional<User> findByUsername(String username);


}
