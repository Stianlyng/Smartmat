package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupAssoRepository extends JpaRepository<UserGroupAsso, UserGroupId> {
}
