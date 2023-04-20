package ntnu.idatt2016.v233.SmartMat.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.Group;

import java.util.Optional;

/**
 * Repository for groups
 * 
 * @author Stian Lyng, Anders Austlid
 * @version 1.1
 * @since 20.04.2023
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    /**
     * Finds a group by its name
     * @param groupName the name of the group to look up
     * @return an optional containing the group if it exists
     */
    Optional<Group> findByGroupName(String groupName);

    /**
     * Finds a group by its id
     * @param groupId the id of the group to look up
     * @return an optional containing the group if it exists
     */
    Optional<Group> findByGroupId(long groupId);
}
