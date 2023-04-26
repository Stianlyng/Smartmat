package ntnu.idatt2016.v233.SmartMat.controller.group;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.service.group.UserGroupAssoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
