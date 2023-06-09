package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the UserGroupAsso association entity
 *
 * @author Pedro, Birk, Anders
 * @version 1.3
 */
public interface UserGroupAssoRepository extends JpaRepository<UserGroupAsso, UserGroupId> {


    /**
     * Creates a new user-group association with the specified user and group IDs and assigns the user as the primary group administrator with ADMIN authority.
     *
     * @param username the username of the user to add to the group
     * @param groupId the ID of the group to add the user to
     * @return an Optional containing the Group object for the newly created user-group association, or an empty Optional if the operation failed
     */
    @Query(value = "insert into user_group ('username', 'group_id', 'primary_group','group_authority') values (:username , :groupId , TRUE , 'ADMIN')",nativeQuery = true)
    Optional<Group> createConnectionAfterCreateGroup(String username, long groupId);

    /**
     * Retrieves a list of UserGroupAsso objects for the specified Group object.
     *
     * @param group the Group object to retrieve information for
     * @return a List of UserGroupAsso objects for the specified Group object
     */
    List<UserGroupAsso> findAllByGroup(Group group);

    /**
     * Finds all UserGroupAsso objects associated with the specified group and user.
     *
     * @param group the group for which to find the user-group associations
     * @param user the user for which to find the user-group associations
     * @return an optional object containing a list of UserGroupAsso objects associated with the specified group and user,
     *         or an empty optional if no such user-group associations exist in the database
     */
    Optional<UserGroupAsso> findAllByGroupAndUser(Group group, User user);

    /**
     * Finds the first UserGroupAsso object associated with the specified user and primary group status.
     *
     * @param user the user for which to find the user-group association
     * @param primaryGroup the primary group status for which to find the user-group association
     * @return an optional object containing the first UserGroupAsso object associated with the specified user and primary group status,
     *         or an empty optional if no such user-group associations exist in the database
     */
    Optional<UserGroupAsso> findFirstByUserAndPrimaryGroup(User user, boolean primaryGroup);

    /**
     * Finds UserGroupAsso by UserGroupId
     *
     * @param userGroupId the UserGroupId to find by
     * @return an optional containing the UserGroupAsso if it exists
     */
    Optional<UserGroupAsso> findByUserGroupId(UserGroupId userGroupId);

    /**
     * Finds primary group for given user
     * @param username the username of the user
     * @return an optional containing the UserGroupAsso if it exists
     */
    Optional<UserGroupAsso> findByUser_UsernameAndPrimaryGroupTrue(String username);

    /**
     * Finds the authority level of a user in a group
     * @param username the username of the user
     * @param groupId the id of the group
     * @return the authority level of the user in the group
     */
    String findAuthorityByUser_UsernameAndGroup_GroupId(String username, long groupId);


    /**
     * Finds all groups a user is a member of
     * @param username the username of the user
     * @return a list of all groups the user is a member of
     */
    List<UserGroupAsso> findAllByUserUsername(String username);
}
