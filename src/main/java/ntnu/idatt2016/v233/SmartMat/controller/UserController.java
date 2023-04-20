package ntnu.idatt2016.v233.SmartMat.controller;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.RegisterUserRequest;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    PasswordEncoder passwordEncoder;

    /**
     * Use this JSON format:
     * {
     *     "username":"kari123",
     *     "password":"sjokoladekake",
     *     "email":"kari.nordman@gmail.com",
     *     "firstName":"kari",
     *     "lastName":"nordmann",
     *     "birthDate":"2001-12-12"
     * }
     *
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
                .authority(Authority.USER)
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .dateOfBirth(user.birthDate())
                .enabled(true)
                .build();

        userService.saveUser(newUser);
        newUser.setPassword(null);
        return ResponseEntity.ok(newUser);
    }


}