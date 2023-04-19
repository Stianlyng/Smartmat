package ntnu.idatt2016.v233.SmartMat.dto.request;

import java.sql.Date;

/**
 * RegisterUser is a record class representing a register request.
 * @param username the username of the user
 * @param password the password of the user
 * @param email the email of the user
 *
 */
public record RegisterUserRequest(String username, String password, String email, String firstName, String lastName,
                                  Date birthDate){
}
