package ntnu.idatt2016.v233.SmartMat.service.group;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserGroupAssoService {

    private UserGroupAssoRepository userGroupAssoRepository;
    private GroupRepository groupRepository;

    public void save(User user, Group group, String userLevel) {
        UserGroupAsso userGroupTable1 = new UserGroupAsso();
        userGroupTable1.setGroup(group);
        userGroupTable1.setUser(user);
        userGroupTable1.setPrimaryGroup(true);
        userGroupTable1.setGroupAuthority(userLevel);
        userGroupTable1.setId(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                .build());

        userGroupAssoRepository.save(userGroupTable1);

        user.addGroup(userGroupTable1);
        group.addUser(userGroupTable1);

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
    public void changePrimaryGroup(long newId, String username){

        try {
            userGroupAssoRepository.unmarkOldPrimaryGroup(username);
        }finally {
            userGroupAssoRepository.markNewPrimaryGroup(username,newId);
        }
    }

}
