package ntnu.idatt2016.v233.SmartMat.dto.request.group;

/**
 * GroupConnectionRequest is a record class representing a request to connect to a group.
 * @param username the username of the user connecting to the group
 * @param linkCode the link code of the group
 */
public record GroupConnectionRequest(String username, String linkCode) {
}
