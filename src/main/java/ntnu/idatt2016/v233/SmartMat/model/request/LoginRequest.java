package ntnu.idatt2016.v233.SmartMat.model.request;

/**
 * LoginRequest is a record class representing a login request.
 *
 * @author Anders
 * @version 1.0
 * @since 04.04.2023
 *
 * @param username
 * @param password
 */
public record LoginRequest(String username, String password) {
}
