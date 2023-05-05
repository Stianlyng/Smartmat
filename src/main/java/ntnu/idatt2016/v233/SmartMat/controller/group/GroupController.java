package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.ChangeAuthorityRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupConnectionRequest;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.GroupRequest;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupDetailsResponse;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.UserAuthorityInfo;
import ntnu.idatt2016.v233.SmartMat.dto.response.group.GroupResponse;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for groups API, providing endpoints for group management
 *
 * @author Anders Austlid, Pedro Cardona, Birk
 * @version 2.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    private final UserService userService;

    /**
     * Gets a group by its id
     * @param groupId the id of the group
     * @param auth the authentication of the user
     * @return a ResponseEntity containing the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable("groupId") long groupId, Authentication auth) {
        if (auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if (!groupService.isUserAssociatedWithGroup(auth.getName(), groupId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        Optional<Group> group = groupService.getGroupById(groupId);
        if (group.isPresent()) {
            List<UserGroupAsso> users = group.get().getUser();
            List<UserAuthorityInfo> userAuthorityInfoList = new ArrayList<>();

            for (UserGroupAsso user : users) {
                Optional<UserGroupAsso> userGroupAsso = groupService.getUserGroupAsso(user.getUser().getUsername(), groupId);
                if (userGroupAsso.isPresent()) {
                    String userAuthority = userGroupAsso.get().getGroupAuthority();
                    userAuthorityInfoList.add(new UserAuthorityInfo(user.getUser().getUsername(), userAuthority));
                }
            }

            GroupDetailsResponse groupDetailsResponse = new GroupDetailsResponse(group.get(), userAuthorityInfoList);

            return ResponseEntity.ok(groupDetailsResponse);
        }
        return ResponseEntity.badRequest().body("Group not found.");
    }


    /**
     * Creates a new group
     *
     * @param groupRequest the group to create
     * @param auth the authentication of the user
     * @return a ResponseEntity containing the created group if it was created successfully,
     * or a 400 if the group name is invalid or already exists, or if the username is invalid
     */
    @PostMapping("/group")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest, Authentication auth) {
        if (groupRequest.groupName() == null || groupRequest.groupName().trim().length() < 3) {
            return ResponseEntity.badRequest().body("Group name must be at least 3 characters long.");
        }

        if (groupService.getGroupByName(groupRequest.groupName()).isPresent()) {
            return ResponseEntity.badRequest().body("Group name already exists.");
        }

        Optional<User> optionalUser = userService.getUserFromUsername(auth.getName());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username.");
        }

        Group group = new Group();
        group.setGroupName(groupRequest.groupName());
        group.setPoints(10);
        group.setLevel(0);

        Group createdGroup = groupService.createGroup(group);

        User user = optionalUser.get();

        UserGroupId userGroupId = UserGroupId.builder()
                .username(user.getUsername())
                .groupId(createdGroup.getGroupId())
                .build();

        Optional<UserGroupAsso> oldPrimaryOptional = groupService.findPrimaryUserGroupAssoForUser(user.getUsername());
        if(oldPrimaryOptional.isPresent()){
            oldPrimaryOptional.get().setPrimaryGroup(false);
            groupService.updateUserGroupAsso(oldPrimaryOptional.get());
        }

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
     * @param auth the authentication of the user
     * @return a ResponseEntity containing the level of the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/{groupId}/level")
    public ResponseEntity<Long> getGroupLevel(@PathVariable("groupId") long groupId, Authentication auth) {
        if (auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if (!groupService.isUserAssociatedWithGroup(auth.getName(), groupId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return groupService.getGroupById(groupId)
                .map(group -> ResponseEntity.ok(group.getLevel()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Returns the progress of the level for the group identified by the given ID.
     * Returns a ResponseEntity containing the progress of the current level as a percentage, or a 404 Not Found response if no Group with the given ID was found.
     *
     * @param groupId the ID of the group to query
     * @param auth    the Authentication object containing the user's credentials
     * @return a ResponseEntity containing the progress of the current level as a percentage, or a 404 Not Found response if no Group with the given ID was found
     */
    @GetMapping("/{groupId}/progress")
    public ResponseEntity<Integer> getProgressOfLevel(@PathVariable("groupId") long groupId, Authentication auth) {
        if(auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if (!groupService.isUserAssociatedWithGroup(auth.getName(), groupId))
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }



        return groupService.getProgressOfLevel(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates the open/closed status of the group with the specified ID.
     *
     * @param groupId the ID of the group to update
     * @param auth the authentication of the user
     * @return a ResponseEntity with a Boolean value indicating whether the operation was successful
     */
    @PutMapping("/{groupId}/changeOpen")
    public ResponseEntity<Boolean> changeOpenValue(@PathVariable("groupId") long groupId, Authentication auth) {
        if (auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if (!groupService.isUserAssociatedWithGroup(auth.getName(), groupId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if (!(groupService.getUserGroupAssoAuthority(auth.getName(), groupId).equals("ADMIN")))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        return groupService.OpenOrCloseGroup(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the HTTP PUT request to change the primary group of a user.
     *
     * @param newPrimaryGroupId the ID of the new primary group
     * @param auth the authentication of the user
     * @return a ResponseEntity object containing an HTTP status code and the updated UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PutMapping("/markNewPrimary/{newPrimaryGroupId}")
    public ResponseEntity<?> markNewPrimaryGroup(@PathVariable("newPrimaryGroupId") long newPrimaryGroupId, Authentication auth) {
        if (!groupService.isUserAssociatedWithGroup(auth.getName(), newPrimaryGroupId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userService.getUserFromUsername(auth.getName());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username.");
        }

        User user = optionalUser.get();

        Optional<UserGroupAsso> oldPrimaryOpt = groupService.findPrimaryUserGroupAssoForUser(user.getUsername());
        if (oldPrimaryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No primary group found for the user.");
        }

        UserGroupAsso oldPrimary = oldPrimaryOpt.get();
        oldPrimary.setPrimaryGroup(false);
        groupService.updateUserGroupAsso(oldPrimary);

        Optional<UserGroupAsso> newPrimaryOpt = groupService.getUserGroupAsso(user.getUsername(), newPrimaryGroupId);
        if (newPrimaryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid new primary group ID.");
        }

        UserGroupAsso newPrimary = newPrimaryOpt.get();
        newPrimary.setPrimaryGroup(true);
        groupService.updateUserGroupAsso(newPrimary);

        userService.updateUser(user);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", user.getUsername());
        responseBody.put("oldPrimaryGroupId", oldPrimary.getGroup().getGroupId());
        responseBody.put("newPrimaryGroupId", newPrimary.getGroup().getGroupId());
        return ResponseEntity.ok(responseBody);
    }

    /**
     * Handles the HTTP POST request to add a new connection between a user and a group.
     *
     * @param groupConnectionRequest the request object containing the username and link code of the user and group to be connected
     * @param auth the authentication of the user
     * @return a ResponseEntity object containing an HTTP status code and the newly created UserGroupAsso object,
     *         or a ResponseEntity object with an HTTP status code indicating that the request was not successful
     */
    @PostMapping("/connection")
    public ResponseEntity<?> addConnection(@RequestBody GroupConnectionRequest groupConnectionRequest, Authentication auth) {
        Optional<Group> optionalGroup = groupService.getGroupByLinkCode(groupConnectionRequest.linkCode());

        if (optionalGroup.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid link code.");
        }

        Optional<User> optionalUser = userService.getUserFromUsername(auth.getName());

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
                .groupAuthority("USER")
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
     * @param authorityRequest the request object containing the username and group ID of the user to change the authority of
     * @param auth the authentication of the user
     * @return a ResponseEntity object containing the updated UserGroupAsso object and an HTTP status code of 200,
     *        or a ResponseEntity object with an HTTP status code of 403 if the user is not authorized to change the authority,
     *         or a ResponseEntity object with an HTTP status code of 404 if the group or user does not exist
     */
    @PutMapping("/groupAuthority")
    public ResponseEntity<?> changeAuthority(@RequestBody ChangeAuthorityRequest authorityRequest,
                                             Authentication auth) {
        Optional<User> groupAdminOpt = userService.getUserFromUsername(auth.getName());
        if (groupAdminOpt.isPresent()) {
            if (auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
                if (!groupService.isUserAssociatedWithGroup(auth.getName(), authorityRequest.groupId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                boolean isUserAdmin = groupService.getUserGroupAssoAuthority(auth.getName(), authorityRequest.groupId()).equals("ADMIN");
                System.out.println(auth.getName() + ((isUserAdmin)  ? " is admin" : " is not admin"));
                if (!isUserAdmin)
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Optional<Group> groupOpt = groupService.getGroupById(authorityRequest.groupId());
            Optional<User> userOpt = userService.getUserFromUsername(authorityRequest.username());

            if (groupOpt.isEmpty() || userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOpt.get();
            UserGroupAsso userGroupAsso = user.getGroup().stream()
                    .filter(asso -> asso.getGroup().getGroupId() == authorityRequest.groupId())
                    .findFirst()
                    .orElse(null);

            if (userGroupAsso != null) {
                userGroupAsso.setGroupAuthority(authorityRequest.authority());
                userService.updateUser(user);
                return ResponseEntity.ok("Authority changed successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Cant find user");
    }

    /**
     * Handles the HTTP DELETE request to remove a user from a group.
     * @param auth the authentication object containing the username of the user
     * @return a ResponseEntity object containing the list of groups the user is associated with and an HTTP status code of 200,
     */
    @GetMapping("/")
    public ResponseEntity<List<UserGroupAsso>> getAllGroupsByUser(Authentication auth) {
        return ResponseEntity.ok(groupService.getUserGroupAssoByUserName(auth.getName()));
    }


    /**
     * Handles the HTTP DELETE request to remove a user from a group.
     * @param groupId the ID of the group to get the members of
     * @param username the username of the user to remove from the group
     * @param auth the authentication object containing the username of the user
     * @return a ResponseEntity object containing the list of groups the
     * user is associated with and an HTTP status code of 200,
     */
    @DeleteMapping("/removeUser/{groupId}/{username}")
    public ResponseEntity<?> removeUserFromGroup(@PathVariable("groupId") long groupId,
                                                 @PathVariable("username") String username,
                                                 Authentication auth) {
        Optional<User> groupAdminOpt = userService.getUserFromUsername(auth.getName());
        if (groupAdminOpt.isPresent()) {
            User groupAdmin = groupAdminOpt.get();
            if (auth.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
                if (!groupService.isUserAssociatedWithGroup(groupAdmin.getUsername(), groupId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                try {
                    if (!username.equals(auth.getName()))
                        if (!(groupService.getUserGroupAssoAuthority(groupAdmin.getUsername(), groupId).equals("ADMIN")))
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            Optional<Group> groupOpt = groupService.getGroupById(groupId);
            Optional<User> userOpt = userService.getUserFromUsername(username);

            if (groupOpt.isEmpty() || userOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOpt.get();
            UserGroupAsso userGroupAsso = user.getGroup().stream()
                    .filter(asso -> asso.getGroup().getGroupId() == groupId)
                    .findFirst()
                    .orElse(null);

            if (userGroupAsso != null) {
                groupService.removeUserFromGroup(userGroupAsso);
                return ResponseEntity.ok("User removed successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Cant find user");
    }
}
