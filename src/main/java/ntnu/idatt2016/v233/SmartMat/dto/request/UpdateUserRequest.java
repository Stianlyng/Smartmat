package ntnu.idatt2016.v233.SmartMat.dto.request;

import java.sql.Date;
import java.util.List;

/**
 * This class represents a request to update a user
 *
 * @param firstName the first name of the user
 * @param lastName the last name of the user
 * @param email the email of the user
 * @param password the password of the user
 * @param birthDate the birth date of the user
 * @param allergies the allergies of the user
 */
public record UpdateUserRequest(String firstName, String lastName,
                                String email, String password, Date birthDate,
                                List<String> allergies) {
}
