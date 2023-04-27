package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupConnectionRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupRequest;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupResponse;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * @return a ResponseEntity containing the created group if it was created successfully, or a 400 if it wasn't
     */
    @PostMapping("/{username}")
    public ResponseEntity<Group> createGroup(@RequestBody Group group,
                                             @PathVariable("username") String username) {

        if(group.getGroupName().equals("") ||
                userService.getUserFromUsername(username).isEmpty() ||
                groupService.getGroupById(group.getGroupId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }


        Group group1 = groupService.createGroup(group);
        group1.addUser(UserGroupAsso.builder()
                        .groupAuthority("ADMIN")
                        .group(group1)
                        .user(userService.getUserFromUsername(username).get())
                .build());

        return groupService.updateGroup(group1).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
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
     * @param groupId the ID of the group
     * @return a ResponseEntity object containing an HTTP status code and the updated UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PutMapping("/markNewPrimary/{username}/{groupId}/{newId}")
    public ResponseEntity<?> markNewPrimaryGroup(@PathVariable("username") String username,
                                                 @PathVariable("groupId") long groupId){
        return userService.getUserFromUsername(username)
                .flatMap(user ->{
                            user.getGroup().forEach(userGroupAsso -> {
                                if(userGroupAsso.getGroup().getGroupId() != groupId){
                                    userGroupAsso.setPrimaryGroup(false);
                                }
                                if(userGroupAsso.getGroup().getGroupId() == groupId){
                                    userGroupAsso.setPrimaryGroup(true);
                                }
                            });
                            return Optional.of(userService.updateUser(user));
                        }
                )
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the HTTP POST request to add a new connection between a user and a group.
     *
     * @param groupConnectionRequest the request object containing the username and link code of the user and group to be connected
     * @return a ResponseEntity object containing an HTTP status code and the newly created UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PostMapping("/connection/{username}/{linkCode}")
    public ResponseEntity<?> addConnection(@PathVariable("username") String username,
                                           @PathVariable("linkCode") String linkCode){
        return groupService.getGroupByLinkCode(linkCode)
                .flatMap(group -> userService.getUserFromUsername(username)
                        .flatMap(user -> {
                            UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                                    .group(group)
                                    .user(user)
                                    .build();
                            user.addGroup(userGroupAsso);
                            userService.updateUser(user);
                            return Optional.of(userGroupAsso);
                        }))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
