package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupRequest;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupResponse;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.group.UserGroupAssoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for groups API, providing endpoints for group management
 *
 * @author Anders Austlid, Pedro Cardona
 * @version 1.1
 * @since 27.04.2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserGroupAssoService userGroupAssoService;

    /**
     * Gets a group by its name
     * @param groupName the name of the group
     * @return a ResponseEntity containing the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/{groupName}")
    public ResponseEntity<Group> getGroupByName(@PathVariable("groupName") String groupName){
        return groupService.getGroupByName(groupName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets a group by its id
     * @param groupId the id of the group
     * @return a ResponseEntity containing the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/id/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable("groupId") long groupId){
        return groupService.getGroupById(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new group
     *
     * @param groupRequest the group to create
     * @return a ResponseEntity containing the created group if it was created successfully, or a 400 if it wasn't
     */
    @PostMapping("/group")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        if(groupService.getGroupByName(groupRequest.groupName()).isPresent()) {
            return ResponseEntity.badRequest().body("Group name already exists");
        }
        if(groupRequest.groupName().equals("")) {
            return ResponseEntity.badRequest().body("Group name cannot be empty");
        }
        Group group = new Group();
        group.setGroupName(groupRequest.groupName());

        Group createdGroup = groupService.createGroup(group);
        userGroupAssoService.addPersonToGroup(groupRequest.username(),createdGroup.getLinkCode(), "ADMIN");

        GroupResponse groupResponse = new GroupResponse(createdGroup.getGroupId(), createdGroup.getLinkCode());
        return ResponseEntity.ok(groupResponse);
    }

    /**
     * Gets the level of a group
     *
     * @param groupId the id of the group
     * @return a ResponseEntity containing the level of the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/{groupId}/level")
    public ResponseEntity<Long> getGroupLevel(@PathVariable("groupId") long groupId) {
        return groupService.getGroupById(groupId)
                .map(group -> ResponseEntity.ok(group.getLevel()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Sets the level of the group identified by the given ID to the level corresponding to the given experience points.
     * Returns a ResponseEntity containing the new level of the group, or a 404 Not Found response if no Group with the given ID was found.
     *
     * @param groupId the ID of the group to update
     * @param exp     the new experience points of the group
     * @return a ResponseEntity containing the new level of the group, or a 404 Not Found response if no Group with the given ID was found
     */
    @PutMapping("/{groupId}/newLevel/{exp}")
    public ResponseEntity<Long> setNewLevel(@PathVariable("groupId") long groupId, @PathVariable("exp") long exp) {
        return groupService.setLevelByGroupId(groupId, exp)
                .map(group -> ResponseEntity.ok(group.getLevel()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Returns the progress of the level for the group identified by the given ID.
     * Returns a ResponseEntity containing the progress of the current level as a percentage, or a 404 Not Found response if no Group with the given ID was found.
     *
     * @param groupId the ID of the group to query
     * @return a ResponseEntity containing the progress of the current level as a percentage, or a 404 Not Found response if no Group with the given ID was found
     */
    @GetMapping("/{groupId}/progress")
    public ResponseEntity<Integer> getProgressOfLevel(@PathVariable("groupId") long groupId) {
        return groupService.getProgressOfLevel(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates the open/closed status of the group with the specified ID.
     *
     * @param groupId the ID of the group to update
     * @return a ResponseEntity with a Boolean value indicating whether the operation was successful
     */
    @PutMapping("/{groupId}/changeOpen")
    public ResponseEntity<Boolean> changeOpenValue(@PathVariable("groupId") long groupId) {
        return groupService.OpenOrCloseGroup(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Returns a response entity containing a list of UserGroupAsso objects related to the given groupId.
     * If no user-group associations are found for the given groupId, a not-found response entity is returned.
     *
     * @param groupId the ID of the group to retrieve user-group associations for
     * @return a response entity containing a list of UserGroupAsso objects related to the given groupId, or a not-found response entity if no associations are found
     */
    @GetMapping("/information/{groupId}")
    public ResponseEntity<List<UserGroupAsso>> getInformationByGroupId(@PathVariable("groupId") long groupId){
        return userGroupAssoService.getInformationByGroupId(groupId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the HTTP PUT request to change the primary group of a user.
     *
     * @param username the username of the user whose primary group is to be changed
     * @param newId the ID of the new primary group
     * @param oldId the ID of the old primary group
     * @return a ResponseEntity object containing an HTTP status code and the updated UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PutMapping("/markNewPrimary/{username}/{oldId}/{newId}")
    public ResponseEntity<?> markNewPrimaryGroup(@PathVariable("username") String username,
                                                 @PathVariable("newId") long newId,
                                                 @PathVariable("oldId") long oldId){
        return userGroupAssoService.changePrimaryGroup(oldId,newId,username).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the HTTP POST request to add a new connection between a user and a group.
     *
     * @param username the username of the user to add to the group
     * @param linkCode the code of the group to which the user is to be added
     * @return a ResponseEntity object containing an HTTP status code and the newly created UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PostMapping("/connection/{username}/{linkCode}")
    public ResponseEntity<?> addConnection(@PathVariable("username") String username,
                                           @PathVariable("linkCode") String linkCode){
        return userGroupAssoService.addPersonToGroup(username,linkCode,"USER").map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    /**
     * Changes the authority level of a user in a group.
     *
     * @param groupId the ID of the group
     * @param username the username of the user whose authority level is to be changed
     * @param authority the new authority level of the user
     * @return a ResponseEntity object containing the updated UserGroupAsso object and an HTTP status code of 200,
     *         or a ResponseEntity object with an HTTP status code of 404 if the group or user does not exist
     */
    @PutMapping("/changeAuthority/{groupId}/{username}/{authority}")
    public ResponseEntity<?> changeAuthority(@PathVariable("groupId") long groupId,
                                             @PathVariable("username") String username,
                                             @PathVariable("authority") String authority){
        return userGroupAssoService.changeAuthorityOfUser(username,groupId,authority).map(ResponseEntity::ok).orElseGet(() ->ResponseEntity.notFound().build());
    }
}
