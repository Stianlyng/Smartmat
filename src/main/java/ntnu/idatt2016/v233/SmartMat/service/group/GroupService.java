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


}
