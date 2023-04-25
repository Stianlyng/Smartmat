package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    /**
     * Gets the fridge of a group
     *
     * @param groupId the id of the group
     * @return an Optional containing the fridge of the group if it exists
     */
    Optional<Fridge> findByGroupId(long groupId);
}
