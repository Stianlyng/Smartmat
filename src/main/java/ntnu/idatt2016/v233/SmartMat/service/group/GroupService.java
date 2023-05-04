package ntnu.idatt2016.v233.SmartMat.service.group;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.repository.ShoppingListRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import ntnu.idatt2016.v233.SmartMat.util.GroupUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for groups
 *
 * @author Anders Austlid & Birk
 * @version 1.2
 * @since 26.04.2023
 */
@AllArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserGroupAssoRepository userGroupAssoRepository;

    private final FridgeRepository fridgeRepository;
    private ShoppingListRepository shoppingListRepository;

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
        group.setFridge(Fridge.builder().group(group).build());
        group.setShoppingList(ShoppingList.builder().group(group).build());

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
            if (realGroup.getOpen() == null)
                realGroup.setOpen(true);

            realGroup.setOpen(!realGroup.getOpen());
            System.out.println(realGroup.getOpen());
            return Optional.of(groupRepository.save(realGroup).getOpen());
        }
        return Optional.empty();
    }

    /**
     * Adds a group to a fridge
     * @param fridgeId the id of the fridge
     * @param groupId the id of the group
     */
    public void addFridgeToGroup(long fridgeId, long groupId) {
        Optional<Fridge> fridge = fridgeRepository.findById(fridgeId);
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        if (fridge.isPresent() && group.isPresent()) {
            fridge.get().setGroup(group.get());
            group.get().setFridge(fridge.get());
            groupRepository.save(group.get());
            fridgeRepository.save(fridge.get());
        }
    }

    /**
     * Updates a group
     * @param group the group to update
     * @return an optional containing the updated group
     */
    public Optional<Group> updateGroup(Group group){
        return Optional.of(groupRepository.save(group));
    }

    public Optional<Group> getGroupByLinkCode(String linkCode) {
        return groupRepository.findByLinkCode(linkCode);
    }

    /**
     * Checks if a user is associated with a group
     * @param username the username of the user
     * @param groupId the id of the group
     * @return true if the user is associated with the group, false otherwise
     */
    public boolean isUserAssociatedWithGroup(String username, Long groupId) {
        UserGroupId userGroupId = UserGroupId.builder()
                .username(username)
                .groupId(groupId)
                .build();

        return userGroupAssoRepository.findById(userGroupId).isPresent();
    }

    /**
     * Updates a user group association
     *
     * @param userGroupAsso the user group association to update
     * @return the updated user group association
     */
    public UserGroupAsso updateUserGroupAsso(UserGroupAsso userGroupAsso) {
        return userGroupAssoRepository.save(userGroupAsso);
    }

    /**
     * Finds the primary group for a user
     * @param username the username of the user
     * @return an optional containing the primary group if it exists
     */
    public Optional<UserGroupAsso> findPrimaryUserGroupAssoForUser(String username) {
        return userGroupAssoRepository.findByUser_UsernameAndPrimaryGroupTrue(username);
    }

    /**
     * Gets a user group association
     * @param username the username of the user
     * @param groupId the id of the group
     * @return
     */
    public Optional<UserGroupAsso> getUserGroupAsso(String username, Long groupId) {
        UserGroupId userGroupId = UserGroupId.builder()
                .username(username)
                .groupId(groupId)
                .build();

        return userGroupAssoRepository.findById(userGroupId);
    }

    public String getUserGroupAssoAuthority(String username, long groupId) {
        Optional<UserGroupAsso> userGroupAsso = getUserGroupAsso(username, groupId);
        return userGroupAsso.map(UserGroupAsso::getGroupAuthority).orElseThrow(() -> new IllegalArgumentException("User is not associated with group"));
    }


    /**
     * Gets all user group associations for a user
     * @param username the username of the user
     * @return a list of all user group associations for the user
     */
    public List<UserGroupAsso> getUserGroupAssoByUserName(String username) {
        return userGroupAssoRepository.findAllByUserUsername(username);
    }

    /**
     * removes user_group relatioon
     * @param username the username of the user
     * @param groupId the id of the group
     * @return true if the user is the owner of the group, false otherwise
     */
    @Transactional
    public boolean removeUserFromGroup(UserGroupAsso userGroup) {
        Group group = groupRepository.findByGroupId(userGroup.getGroup().getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group does not exist"));

        group.getUser().remove(userGroup);

        if (group.getUser().isEmpty()) {
            groupRepository.delete(group);
        } else {
            groupRepository.save(group);
        }

        userGroupAssoRepository.delete(userGroup);
        return true;
    }
}
