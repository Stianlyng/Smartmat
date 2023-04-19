package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.Groups;

/**
 * Repository for groups
 * 
 * @author Stian Lyng
 * @version 1.0
 * @since 04.04.2023
 */
public interface GroupRepository extends JpaRepository<Groups, Long> {

}
