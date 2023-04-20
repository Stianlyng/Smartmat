package ntnu.idatt2016.v233.SmartMat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.Group;

/**
 * Repository for groups
 * 
 * @author Stian Lyng
 * @version 1.0
 * @since 19.04.2023
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

}
