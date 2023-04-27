package ntnu.idatt2016.v233.SmartMat.service.user;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.AllergyRepository;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import ntnu.idatt2016.v233.SmartMat.service.RecipeService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

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

    private RecipeService recipeService;

    AllergyRepository allergyRepository;

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

    /**
     * adds recipe to users favorite recipes
     * @param username username of user
     * @param recipeId id of recipe
     * @throws RuntimeException if user or recipe does not exist or user
     */
    public void addFavoriteRecipe(String username, long recipeId) throws RuntimeException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("did not find user"));
        Recipe tempRecipe = recipeService.getRecipeById(recipeId)
                .orElseThrow(()-> new RuntimeException("did not find recipe"));
        user.addRecipe(tempRecipe);

        recipeService.addUserToRecipe(tempRecipe, user);
        userRepository.save(user);
    }



    /**
     * Adds allergy to user
     * @param username username of user
     * @param allergyName name of allergy
     * @return user with added allergy
     * @throws EntityNotFoundException if user or allergy does not exist
     */
    public Optional<User> addAllergyToUser(String username, String allergyName){

        Optional<User> user = userRepository.findByUsername(username);
        Optional<Allergy> allergy = allergyRepository.findByName(allergyName);

        if (user.isPresent() && allergy.isPresent()){
            user.get().addAllergy(allergy.get());
            return Optional.of(userRepository.save(user.get()));
        } else if (!user.isPresent()) {
            throw new EntityNotFoundException("User not found");
        } else if (!allergy.isPresent()) {
            throw new EntityNotFoundException("Allergy not found");
        }
        return Optional.empty();
    }

    /**
     * Removes allergy from user
     * @param username username of user
     * @param allergyName name of allergy
     * @return user with removed allergy
     */
    public Optional<User> removeAllergyFromUser(String username, String allergyName){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Allergy> allergy = allergyRepository.findByName(allergyName);

        if (user.isPresent() && allergy.isPresent()){
            user.get().getAllergies().remove(allergy.get());
            return Optional.of(userRepository.save(user.get()));
        } else if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else if (allergy.isEmpty()) {
            throw new EntityNotFoundException("Allergy not found");
        }
        return Optional.empty();
    }
}