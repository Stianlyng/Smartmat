package ntnu.idatt2016.v233.SmartMat.model.user;


/**
 * User is a record class representing a user in the system.
 *
 * @author Anders
 * @version 1.0
 * @since 04.04.2023
 *
 * @param username The username of the user.
 * @param password The password of the user.
 * @param enabled Whether the user is enabled.
 */

public record User(String username, String password,
                   boolean enabled, Profile profile) {
}
