package ntnu.idatt2016.v233.SmartMat.entity.request;

/**
 * LoginRequest is a record class representing a login request.
 *
 * @author Anders
 * @version 1.0
 * @since 18.04.2023
 *
 * @param username
 * @param password
 */
public record LoginRequest(String username, String password) {
}
