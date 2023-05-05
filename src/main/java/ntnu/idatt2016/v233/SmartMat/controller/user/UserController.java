package ntnu.idatt2016.v233.SmartMat.controller.user;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.product.AllergyRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.user.RegisterUserRequest;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.dto.request.user.UpdateUserRequest;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * The user controller is responsible for handling requests related to users.
 * It uses the user service to handle the requests.
 * @author Birk
 * @version 1.2
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    PasswordEncoder passwordEncoder;

    /**
     * create a new user in the database
     * uses the user service
     * @param user The user to be registered.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest user) {

        if(user.username() == null || user.username().trim().isEmpty() || user.username().length() > 50 ||
                user.password() == null || user.password().trim().isEmpty() || user.password().length() > 50 ||
                user.email() == null || user.email().trim().isEmpty() || user.email().length() > 50 ||
                user.firstName() == null || user.firstName().trim().isEmpty() || user.firstName().length() > 50 ||
                user.lastName() == null || user.lastName().trim().isEmpty() || user.lastName().length() > 50 ||
                user.birthDate() == null) {
            return ResponseEntity.badRequest()
                    .build();
        }

        if(userService.getUserFromUsername(user.username()).isPresent() ||
                userService.getUserFromEmail(user.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User newUser = User.builder()
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .dateOfBirth(user.birthDate())
                .enabled(true)
                .authority(Authority.USER)
                .build();
        userService.saveUser(newUser);
        newUser.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /**
     * Get a user from the database.
     * @param username The username of the user to be fetched.
     * @param authentication The authentication object of the user.
     * @return The user with the given username.
     */
    @GetMapping("/get/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username, Authentication authentication) {
        if (!username.equals(authentication.getName()) &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        return userService.getUserFromUsername(username)
                .map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Adds the specified allergy to the user with the given username.
     *
     * @param allergyRequest the request object containing the username and allergy name
     * @param authentication The authentication object of the user.
     * @return a ResponseEntity with a boolean indicating whether the operation was successful
     */
    @PostMapping("/addAllergy")
    public ResponseEntity<String> addAllergyToUser(@RequestBody AllergyRequest allergyRequest,
                                                    Authentication authentication) {
        if(!allergyRequest.getUsername().equals(authentication.getName()) &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return userService.addAllergyToUser(allergyRequest.getUsername(), allergyRequest.getAllergyName());
    }

    /**
     * Deletes the specified allergy from the user with the given username.
     *
     * @param allergyRequest the request object containing the username and allergy name
     * @param authentication The authentication object of the user.
     * @return a ResponseEntity with a boolean indicating whether the operation was successful
     */
    @DeleteMapping("/deleteAllergy")
    public ResponseEntity<String> deleteAllergyFromUser(@RequestBody AllergyRequest allergyRequest,
                                                         Authentication authentication) {
        if(!allergyRequest.getUsername().equals(authentication.getName()) &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Authority.ADMIN.name())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return userService.removeAllergyFromUser(allergyRequest.getUsername(), allergyRequest.getAllergyName());

    }


    /**
     * Update a user in the database.
     * @param username The username of the user to be updated.
     * @param updateUser The new values for the user.
     * @param authentication The authentication object of the user.
     * @return The updated user.
     */
    @PutMapping("/update/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest updateUser,
                                           Authentication authentication) {
        if(!username.equals(authentication.getName()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Optional<User> user = userService.getUserFromUsername(username);


        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        User userEntity = user.get();

        if(updateUser.firstName() != null &&
                !updateUser.firstName().trim().isEmpty() && updateUser.firstName().length() <= 50){
            userEntity.setFirstName(updateUser.firstName());
        }

        if(updateUser.lastName() != null &&
                !updateUser.lastName().trim().isEmpty() && updateUser.lastName().length() <= 50){
            userEntity.setLastName(updateUser.lastName());
        }

        if(updateUser.email() != null &&
                !updateUser.email().trim().isEmpty() && updateUser.email().length() <= 50){
            userEntity.setEmail(updateUser.email());
        }

        if(updateUser.password() != null &&
                !updateUser.password().trim().isEmpty() && updateUser.password().length() <= 50){
            userEntity.setPassword(passwordEncoder.encode(updateUser.password()));
        }

        if(updateUser.birthDate() != null){
            userEntity.setDateOfBirth(updateUser.birthDate());
        }


        if(updateUser.allergies() != null){
            userEntity.getAllergies().stream().filter(allergy -> !updateUser.allergies().contains(allergy.getName()))
                    .forEach(allergy -> userService.removeAllergyFromUser(username, allergy.getName()));

            updateUser.allergies().stream().filter(allergy -> userEntity.getAllergies().stream()
                    .noneMatch(allergy1 -> allergy1.getName().equals(allergy))).forEach(
                    allergy -> userService.addAllergyToUser(username, allergy)
            );

        }

        userService.updateUser(userEntity);


        userEntity.setPassword(null);

        return ResponseEntity.ok(userEntity);

    }
}