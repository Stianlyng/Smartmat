package ntnu.idatt2016.v233.SmartMat.controller;

import ntnu.idatt2016.v233.SmartMat.dto.request.LoginRequest;
import ntnu.idatt2016.v233.SmartMat.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController defines the endpoint for user authentication,
 * authenticating user credentials and generating a JWT token.
 *
 * @author Anders
 * @version 1.0
 */

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new AuthController with the specified TokenService and AuthenticationManager.
     * The parameters may be autowired by Spring BOOT.
     *
     * @param tokenService The TokenService instance responsible for generating tokens.
     * @param authenticationManager The AuthenticationManager instance responsible for authenticating user credentials.
     */

    public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Generates a token for the given user login request if the authentication is successful.
     * Input data is validated before the authentication is attempted.
     *
     * @param userLogin A LoginRequest object containing the user's username and password.
     * @return A ResponseEntity containing the generated token or an error message.
     */
    @PostMapping("/credentials")
    public ResponseEntity<?> token(@RequestBody LoginRequest userLogin) {
        try {
            if(userLogin.username() == null || userLogin.username().trim().isEmpty() || userLogin.username().length() > 50 ||
                    userLogin.password() == null || userLogin.password().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Invalid input.");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.username(),
                            userLogin.password()
                    )
            );

            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to generate token. Please try again later.");
        }
    }
}
