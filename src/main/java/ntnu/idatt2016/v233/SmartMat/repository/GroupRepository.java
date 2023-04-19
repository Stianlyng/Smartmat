package ntnu.idatt2016.v233.SmartMat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ntnu.idatt2016.v233.SmartMat.entity.Group;

/**
 * Repository for groups
 * 
 * @author Stian Lyng
 * @version 1.0
 * @since 04.04.2023
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Gets a group by name
     *
     * @param name the name of the group
     * @return an optional containing the group if it exists
     */
    Optional<Group> findByName(String name);

    }
