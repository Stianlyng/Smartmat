package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Products
 * @author Stian
 * @version 1.0
 */
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

    /**
     * Gets all fridge items by their group id
     *
     * @param id the id of the group
     * @return a list of fridge items
     */
    List<Fridge> findAllByGroupId(long id);

}