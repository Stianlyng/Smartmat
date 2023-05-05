package ntnu.idatt2016.v233.SmartMat.dto.response.group;

/**
 * UserAuthorityInfo is a record class representing a response to a group request.
 * @param username the username of the user
 * @param authority the authority of the user
 */
public record UserAuthorityInfo(String username, String authority) {}
