package ntnu.idatt2016.v233.SmartMat.dto.request.user;

/**
 * LoginRequest is a record class representing a login request.
 * @param username
 * @param password
 */
public record LoginRequest(String username, String password) {
}
