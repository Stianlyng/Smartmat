package ntnu.idatt2016.v233.SmartMat.service.group;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import ntnu.idatt2016.v233.SmartMat.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserGroupAssoService {

    private UserGroupAssoRepository userGroupAssoRepository;
    private GroupRepository groupRepository;
    private final UserRepository userRepository;

    public void save(User user, Group group, String authority) {
        UserGroupAsso userGroupTable1 = new UserGroupAsso();
        userGroupTable1.setGroup(group);
        userGroupTable1.setUser(user);
        userGroupTable1.setPrimaryGroup(false);
        userGroupTable1.setGroupAuthority(authority);
        userGroupTable1.setId(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                .build());

        userGroupAssoRepository.save(userGroupTable1);

        user.addGroup(userGroupTable1);
        group.addUser(userGroupTable1);

        groupRepository.save(group);
        userRepository.save(user);

    }

    /**
     * Retrieves a list of UserGroupAsso objects for the specified group ID.
     *
     * @param id the ID of the group to retrieve information for
     * @return an Optional containing a list of UserGroupAsso objects for the specified group ID, or an empty Optional if no information is found
     */
    public Optional<List<UserGroupAsso>> getInformationByGroupId(long id){
        if (groupRepository.findByGroupId(id).isPresent()){
            List<UserGroupAsso> list = userGroupAssoRepository.findAllByGroup(groupRepository.findByGroupId(id).get());
            if(!list.isEmpty()) return Optional.of(list);
        }
        return Optional.empty();
    }

    /**
     * Changes the primary group of a user by unmarking the current primary group and marking a new primary group.
     *
     * @param newId The ID of the new primary group.
     * @param username The username of the user whose primary group is being changed.
     */
    public Optional<Object> changePrimaryGroup(long oldId, long newId, String username){
        Optional<Group> oldGroup = groupRepository.findByGroupId(oldId);
        Optional<Group> newGroup = groupRepository.findByGroupId(newId);
        Optional<User> user =  userRepository.findByUsername(username);

        if (newGroup.isEmpty()) return Optional.empty();
        if (user.isEmpty()) return Optional.empty();

        UserGroupAsso userGroupAsso;
        if (oldGroup.isPresent()){
            userGroupAsso = userGroupAssoRepository.findAllByGroupAndUser(oldGroup.get(),user.get()).get();
            userGroupAsso.setPrimaryGroup(false);
            userGroupAssoRepository.save(userGroupAsso);
        }
        userGroupAsso = userGroupAssoRepository.findAllByGroupAndUser(newGroup.get(),user.get()).get();
        userGroupAsso.setPrimaryGroup(true);
        userGroupAssoRepository.save(userGroupAsso);

        return Optional.of(userGroupAsso);
    }

    /**
     * Adds a new user to a group.
     *
     * @param username the username of the user to add
     * @param linkCode the link code of the group to which the user is to be added
     * @param authority the authority level of the user in the group
     * @return an Optional object containing the UserGroupAsso object of the user in the group,
     *         or an empty Optional if the group or user does not exist, or if the user is already in the group
     */
    public Optional<Object> addPersonToGroup(String username, String linkCode, String authority){
        Optional<Group> group = groupRepository.findByLinkCode(linkCode);
        Optional<User> user =  userRepository.findByUsername(username);
        if(group.isEmpty()) return Optional.empty();
        if(user.isEmpty()) return Optional.empty();
        if(userGroupAssoRepository.findAllByGroupAndUser(group.get(),user.get()).isPresent()) return Optional.of("User is already in this group!");
        save(user.get(),group.get(),authority);
        changePrimaryGroup(userGroupAssoRepository.findFirstByUserAndPrimaryGroup(user.get(),true).get().getGroup().getGroupId(), group.get().getGroupId(),username);
        return Optional.of(userGroupAssoRepository.findAllByGroupAndUser(group.get(),user.get()).get());
    }

    /**
     * Changes the authority of a user in a group and updates the primary group if necessary.
     * @param username the username of the user whose authority will be changed.
     * @param groupId the ID of the group in which the user's authority will be changed.
     * @param authority the new authority level for the user in the group.
     * @return an Optional containing the updated UserGroupAsso object, or an empty Optional if the user or group does not exist, or if the user is not in the group.
     */
    public Optional<Object> changeAuthorityOfUser(String username, long groupId, String authority){
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        Optional<User> user =  userRepository.findByUsername(username);
        if(group.isEmpty()) return Optional.empty();
        if(user.isEmpty()) return Optional.empty();
        if(userGroupAssoRepository.findAllByGroupAndUser(group.get(),user.get()).isEmpty()) return Optional.of("User is not in this group!");
        save(user.get(),group.get(),authority);
        changePrimaryGroup(userGroupAssoRepository.findFirstByUserAndPrimaryGroup(user.get(),true).get().getGroup().getGroupId(), group.get().getGroupId(),username);
        return Optional.of(userGroupAssoRepository.findAllByGroupAndUser(group.get(),user.get()).get());
    }

}
