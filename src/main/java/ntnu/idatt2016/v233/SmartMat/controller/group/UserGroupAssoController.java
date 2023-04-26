package ntnu.idatt2016.v233.SmartMat.controller.group;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.service.group.UserGroupAssoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The UserGroupAssoController class is a REST controller that handles HTTP requests related to the user-group association.
 * It provides endpoints for getting information about users in a group.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/userGroup")
public class UserGroupAssoController {

    private final UserGroupAssoService userGroupAssoService;

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

}
