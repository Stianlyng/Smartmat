package ntnu.idatt2016.v233.SmartMat.repository.group;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for groups
 * 
 * @author Stian Lyng, Anders Austlid, Pedro Cardona
 * @version 1.3
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    /**
     * Finds a group by group name
     * @param name the name of the group
     * @return the group with the given name if it exists
     */
    Optional<Group> findByGroupName(String name);


    /**
     * Gets group level by group id
     *
     * @param id the id of the group
     * @return the level of the group
     */
    Optional<Long> getLevelByGroupId(long id);

    /**
     * Finds a group by group id
     * @param id the id of the group
     * @return the group with the given id if it exists
     */
    Optional<Group> findByGroupId(long id);

    /**
     * Gets link codes of all groups
     * @return a list of all link codes
     */
    @Query(value = "SELECT link_code FROM  groups", nativeQuery = true)
    List<String> findAllLinkCode();

    /**
     * Gets a group with a given link code
     * @param linkCode the link code of the group
     * @return the group with the given link code if it exists
     */
    Optional<Group> findByLinkCode(String linkCode);

    /**
     * Gets the amount of users in a group with a given group id
     * @param groupId the id of the group
     * @return the amount of users in the group
     */
    @Query(value = "SELECT count(*) FROM user_group where group_id = :groupId", nativeQuery = true)
    int countAllUserInGroup(@Param("groupId") long groupId);

}
