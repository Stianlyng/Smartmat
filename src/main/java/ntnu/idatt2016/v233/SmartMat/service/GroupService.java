package ntnu.idatt2016.v233.SmartMat.service;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for groups
 *
 * @author Anders Austlid
 * @version 1.0
 * @since 20.04.2023
 */
@AllArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    Optional<Group> 
}
