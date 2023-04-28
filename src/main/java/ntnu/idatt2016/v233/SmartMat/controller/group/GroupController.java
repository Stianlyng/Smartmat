package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupConnectionRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupRequest;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupResponse;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    private final UserService userService;

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
     * @return a ResponseEntity containing the created group if it was created successfully,
     * or a 400 if the group name is invalid or already exists, or if the username is invalid
     */
    @PostMapping("/group")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        if (groupRequest.groupName() == null || groupRequest.groupName().trim().length() < 3) {
            return ResponseEntity.badRequest().body("Group name must be at least 3 characters long.");
        }

        if (groupService.getGroupByName(groupRequest.groupName()).isPresent()) {
            return ResponseEntity.badRequest().body("Group name already exists.");
        }

        Optional<User> optionalUser = userService.getUserFromUsername(groupRequest.username());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username.");
        }

        Group group = new Group();
        group.setGroupName(groupRequest.groupName());
        Group createdGroup = groupService.createGroup(group);

        User user = optionalUser.get();

        UserGroupId userGroupId = UserGroupId.builder()
                .username(user.getUsername())
                .groupId(createdGroup.getGroupId())
                .build();

        createdGroup.addUser(UserGroupAsso.builder()
                .id(userGroupId)
                .primaryGroup(true)
                .groupAuthority("ADMIN")
                .group(createdGroup)
                .user(user)
                .build());

        GroupResponse groupResponse = new GroupResponse(createdGroup.getGroupId(), createdGroup.getLinkCode());

        groupService.updateGroup(createdGroup);

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
        return groupService.getGroupById(groupId)
                .map(group -> ResponseEntity.ok(group.getUser()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the HTTP PUT request to change the primary group of a user.
     *
     * @param username the username of the user whose primary group is to be changed
     * @param newPrimaryGroupId the ID of the new primary group
     * @return a ResponseEntity object containing an HTTP status code and the updated UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PutMapping("/markNewPrimary/{username}/{newPrimaryGroupId}")
    public ResponseEntity<?> markNewPrimaryGroup(@PathVariable("username") String username,
                                                 @PathVariable("newPrimaryGroupId") long newPrimaryGroupId) {
        Optional<User> optionalUser = userService.getUserFromUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username.");
        }

        User user = optionalUser.get();

        Optional<UserGroupAsso> oldPrimaryOpt = groupService.findPrimaryUserGroupAssoForUser(username);
        if (oldPrimaryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No primary group found for the user.");
        }

        UserGroupAsso oldPrimary = oldPrimaryOpt.get();
        oldPrimary.setPrimaryGroup(false);
        groupService.updateUserGroupAsso(oldPrimary);

        Optional<UserGroupAsso> newPrimaryOpt = groupService.getUserGroupAsso(username, newPrimaryGroupId);
        if (newPrimaryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid new primary group ID.");
        }

        UserGroupAsso newPrimary = newPrimaryOpt.get();
        newPrimary.setPrimaryGroup(true);
        groupService.updateUserGroupAsso(newPrimary);

        userService.updateUser(user);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", username);
        responseBody.put("oldPrimaryGroupId", oldPrimary.getGroup().getGroupId());
        responseBody.put("newPrimaryGroupId", newPrimary.getGroup().getGroupId());
        return ResponseEntity.ok(responseBody);
    }

    /**
     * Handles the HTTP POST request to add a new connection between a user and a group.
     *
     * @param groupConnectionRequest the request object containing the username and link code of the user and group to be connected
     * @return a ResponseEntity object containing an HTTP status code and the newly created UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PostMapping("/connection")
    public ResponseEntity<?> addConnection(@RequestBody GroupConnectionRequest groupConnectionRequest) {
        Optional<Group> optionalGroup = groupService.getGroupByLinkCode(groupConnectionRequest.linkCode());

        if (optionalGroup.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid link code.");
        }

        Optional<User> optionalUser = userService.getUserFromUsername(groupConnectionRequest.username());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username.");
        }

        Group group = optionalGroup.get();
        User user = optionalUser.get();

        if (groupService.isUserAssociatedWithGroup(user.getUsername(), group.getGroupId())) {
            return ResponseEntity.badRequest().body("User is already associated with the group.");
        }

        UserGroupId userGroupId = UserGroupId.builder()
                .username(user.getUsername())
                .groupId(group.getGroupId())
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(userGroupId)
                .group(group)
                .user(user)
                .build();

        user.addGroup(userGroupAsso);
        userService.updateUser(user);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("groupId", group.getGroupId());
        responseBody.put("username", user.getUsername());

        return ResponseEntity.ok(responseBody);
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
        return groupService.getGroupById(groupId).flatMap(group -> userService.getUserFromUsername(username)
                .flatMap(user -> {
                    UserGroupAsso userGroupAsso = user.getGroup().stream()
                            .filter(asso -> asso.getGroup().getGroupId() == groupId)
                            .findFirst()
                            .orElse(null);
                    if(userGroupAsso != null){
                        userGroupAsso.setGroupAuthority(authority);
                        userService.updateUser(user);
                        return Optional.of(userGroupAsso);
                    }
                    return Optional.empty();
                }))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
