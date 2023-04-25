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
    @PutMapping("/group/{groupId}/newLevel/{exp}")
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
    @GetMapping("/group/{groupId}/progress")
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
    @PutMapping("/group/{groupId}/changeOpen")
    public ResponseEntity<Boolean> changeOpenValue(@PathVariable("groupId") long groupId) {
        return groupService.OpenOrCloseGroup(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
