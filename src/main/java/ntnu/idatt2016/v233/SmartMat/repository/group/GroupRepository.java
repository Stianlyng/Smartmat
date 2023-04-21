package ntnu.idatt2016.v233.SmartMat.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.group.Group;

import java.util.Optional;

/**
 * Repository for groups
 * 
 * @author Stian Lyng, Anders Austlid
 * @version 1.1
 * @since 20.04.2023
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String name);
}
