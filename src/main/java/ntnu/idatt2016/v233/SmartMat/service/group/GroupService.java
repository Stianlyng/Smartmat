package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.util.GroupUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for groups
 *
 * @author Anders Austlid
 * @version 1.1
 * @since 25.04.2023
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
        String code = GroupUtil.generateUniqueCode();
        List<String> codes = groupRepository.findAllLinkCode();
        while (codes.contains(code)){
            code = GroupUtil.generateUniqueCode();
        }
        group.setLinkCode(code);
        return groupRepository.save(group);
    }

    /**
     * Gets the level of a group
     *
     * @param id the id of the group
     * @return the level of the group
     */
    public Optional<Long> getLevelByGroupId(long id) {
        return groupRepository.getLevelByGroupId(id);
    }

    /**
     * Sets the level of the group identified by the given ID to the level corresponding to the given experience points.
     *
     * @param id  the ID of the group to update
     * @param exp the new experience points of the group
     * @return an Optional containing the updated Group, or an empty Optional if no Group with the given ID was found
     */
    public Optional<Group> setLevelByGroupId(long id, long exp) {
        Optional<Group> answer = groupRepository.findByGroupId(id);
        if (answer.isPresent()) {
            Group realGroup = answer.get();
            realGroup.setPoints(exp);
            realGroup.setLevel(GroupUtil.getLevel(exp));
            return Optional.of(groupRepository.save(realGroup));
        }
        return Optional.empty();
    }

    /**
     * Returns the progress of the level for the group identified by the given ID.
     *
     * @param id the ID of the group to query
     * @return an Optional containing the progress of the current level as a percentage, or an empty Optional if no Group with the given ID was found
     */
    public Optional<Integer> getProgressOfLevel(long id) {
        Optional<Group> answer = groupRepository.findByGroupId(id);
        if (answer.isPresent()) {
            Group realGroup = answer.get();
            return Optional.of(GroupUtil.getProgressOfLevel(realGroup.getPoints()));
        }
        return Optional.empty();
    }

    /**
     * Updates the open/closed status of the group with the specified ID.
     *
     * @param id the ID of the group to update
     * @return an Optional with a Boolean value indicating whether the operation was successful
     */
    public Optional<Boolean> OpenOrCloseGroup(long id){
        Optional<Group> answer = groupRepository.findByGroupId(id);
        if (answer.isPresent()) {
            Group realGroup = answer.get();
            realGroup.setOpen(!realGroup.getOpen());
            System.out.println(realGroup.getOpen());
            return Optional.of(groupRepository.save(realGroup).getOpen());
        }
        return Optional.empty();
    }
}
