package ntnu.idatt2016.v233.SmartMat.entity.user;

/**
 * Profile is a record class representing a profile in the system.
 *
 * @author Anders
 * @version 1.0
 * @since 04.04.2023
 *
 * @param id The id of the profile.
 * @param username The username of the profile.
 * @param firstName The first name of the profile.
 * @param lastName The last name of the profile.
 * @param email The email of the profile.
 */
public record Profile(long id, String username, String firstName,
                      String lastName, String email) {
}
