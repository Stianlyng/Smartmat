package ntnu.idatt2016.v233.SmartMat.dto.request.group;

/**
 * ChangeAuthorityRequest is a record class representing a request to change the authority of a user in a group.
 * @param username the username of the user
 * @param groupId the id of the group
 * @param authority the new authority of the user
 */
public record ChangeAuthorityRequest(String username, long groupId, String authority) {}

