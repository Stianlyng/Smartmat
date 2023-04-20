package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserService is a class that implements the UserDetailsService interface.
 * @author Birk
 * @version 1.1
 * @since 20.04.2023
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;


    /**
     * gets user from username out of database
     * @param username username of user
     * @return user
     */
    public Optional<User> getUserFromUsername(String username){
        return userRepository.findByUsername(username);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * update user if it already exists
     * cant update username from this
     * 
     * @param user user to update
     * @return optional with user if succeded else return null
     */
    public User updateUser(User user){
        if (this.getUserFromUsername(user.getUsername()).isPresent()){
            return userRepository.save(user);
        }
        throw new UsernameNotFoundException("did not find user");
    }

    /**
     * gets password from user
     * @param userName username of user
     * @return password of user
     */
    public String getPassword(String userName){
        return userRepository.findByUsername(userName)
                .orElseThrow(()-> new UsernameNotFoundException("did not find user"))
                .getPassword();
    }

    /**
     * saves user to database
     * @param user user to save
     * @return saved user
     */
    public User saveUser(User user){
        if (this.getUserFromUsername(user.getUsername()).isPresent())
            throw new RuntimeException("user already exists");
        return userRepository.save(user);
    }

    /**
     * saves list of users to database
     * @param users list of users to save
     * @return list of saved users
     */
    public List<User> saveUsers(List<User> users){

        return userRepository.saveAll(users);
    }


    /**
     * deletes user from database
     * @param user user to delete
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * gets user from email out of database
     * @param email email of user
     * @return user
     */
    public Optional<User> getUserFromEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
