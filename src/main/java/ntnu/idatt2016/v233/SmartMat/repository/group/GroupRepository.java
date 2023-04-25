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
 * @author Stian Lyng, Anders Austlid
 * @version 1.3
 * @since 24.04.2023
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    /**
     * Finds a group by group name
     * @param name the name of the group
     * @return the group with the given name if it exists
     */
    Optional<Group> findByGroupName(String name);

    /**
     * Finds all groups by achievement name
     * @param achievementName the name of the achievement
     * @return list of groups with the given achievement
     */
    List<Group> findAllByAchievementsAchievementName(String achievementName);

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

    @Query(value = "SELECT link_code FROM  groups", nativeQuery = true)
    List<String> findAllLinkCode();
}
