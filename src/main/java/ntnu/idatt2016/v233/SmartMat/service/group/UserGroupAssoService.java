package ntnu.idatt2016.v233.SmartMat.service.group;


import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.UserGroupAssoRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserGroupAssoService {

    private UserGroupAssoRepository userGroupAssoRepository;

    public void save(User user, Group group, boolean primaryGroup) {
        UserGroupAsso userGroupTable1 = new UserGroupAsso();
        userGroupTable1.setGroup(group);
        userGroupTable1.setUser(user);
        userGroupTable1.setPrimaryGroup(primaryGroup);
        userGroupTable1.setId(UserGroupId.builder()
                        .groupId(group.getGroupId())
                        .username(user.getUsername())
                .build());

        userGroupAssoRepository.save(userGroupTable1);

        user.addGroup(userGroupTable1);
        group.addUser(userGroupTable1);

    }
}
