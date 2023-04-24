package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for groups API, providing endpoints for group management
 *
 * @author Anders Austlid
 * @version 1.0
 * @since 20.04.2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    /**
     * Gets a group by its name
     * @param groupName the name of the group
     * @return a ResponseEntity containing the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/group/{groupName}")
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
    @GetMapping("/group/id/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable("groupId") long groupId){
        return groupService.getGroupById(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new group
     *
     * @param group the group to create
     * @return a ResponseEntity containing the created group if it was created successfully, or a 400 if it wasn't
     */
    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        if(groupService.getGroupById(group.getGroupId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if(group.getGroupName().equals("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(groupService.createGroup(group));
    }

    /**
     * Gets the level of a group
     *
     * @param groupId the id of the group
     * @return a ResponseEntity containing the level of the group if it exists, or a 404 if it doesn't
     */
    @GetMapping("/group/{groupId}/level")
    public ResponseEntity<Integer> getGroupLevel(@PathVariable("groupId") long groupId) {
        return groupService.getGroupById(groupId)
                .map(group -> ResponseEntity.ok(group.getLevel()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
