package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for groups
 *
 * @author Anders Austlid
 * @version 1.0
 * @since 20.04.2023
 */
@AllArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    /**
     * Gets a group by its name
     * @param name the name of the group
     * @return an optional containing the group if it exists
     */
    public Optional<Group> getGroupByName(String name) {
        return groupRepository.findByGroupName(name);
    }

    /**
     * Gets a group by its id
     * @param id the id of the group
     * @return an optional containing the group if it exists
     */
    public Optional<Group> getGroupById(long id) {
        return groupRepository.findById(id);
    }

    /**
     * Creates a new group
     *
     * @param group the group to create
     *              the group must not already exist
     *              the group must have a name
     * @return the created group
     */
    public Group createGroup(Group group) {
        if(group.getGroupName() == null || group.getGroupName().isEmpty())
            throw new IllegalArgumentException("Group must have a name");
        if(groupRepository.findByGroupName(group.getGroupName()).isPresent())
            throw new IllegalArgumentException("Group already exists");
        return groupRepository.save(group);
    }
}
