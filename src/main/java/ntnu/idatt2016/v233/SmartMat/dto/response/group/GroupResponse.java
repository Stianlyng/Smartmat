package ntnu.idatt2016.v233.SmartMat.dto.response.group;

/**
 * GroupResponse is a record class representing a response to a group request.
 * @param groupId the id of the group
 * @param linkCode the link code of the group
 */
public record GroupResponse(long groupId, String linkCode) {
}
