package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserGroupAssoRepository extends JpaRepository<UserGroupAsso, UserGroupId> {

    /**
     * Retrieves a list of UserGroupAsso objects for the specified Group object.
     *
     * @param group the Group object to retrieve information for
     * @return a List of UserGroupAsso objects for the specified Group object
     */
    List<UserGroupAsso> findAllByGroup(Group group);

    /**
     * Unmarks the old primary group for the given user by setting the 'primary_group' column to FALSE in the 'user_group' table.
     *
     * @param username the username of the user
     */
    @Query(value = "UPDATE user_group SET primary_group = FALSE where username = :username", nativeQuery = true)
    void unmarkOldPrimaryGroup(@Param("username") String username);

    /**
     * Marks the given group as the new primary group for the given user by setting the 'primary_group' column to TRUE in the 'user_group' table.
     *
     * @param username the username of the user
     * @param groupId the ID of the group to mark as primary
     */
    @Query(value = "UPDATE user_group SET primary_group = TRUE where username = :username and group_id = :groupId ", nativeQuery = true)
    void markNewPrimaryGroup(@Param("username") String username,@Param("groupId") long groupId);

}
