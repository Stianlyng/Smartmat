package ntnu.idatt2016.v233.SmartMat.repository.group;

import java.util.List;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WasteRepository extends JpaRepository<Waste, Long> {
    Optional<List<Waste>> findByGroupId( Group groupId);


    /**
     * Returns a list of all waste items of a given category from a specific group.
     *
     * @param groupId the ID of the group
     * @param categoryName the name of the category
     * @return an Optional containing a List of Waste objects if at least one waste item is found,
     *         or an empty Optional if no waste items are found
     */
    @Query(value = "SELECT * FROM wastes WHERE group_id = :groupId AND ean IN (SELECT ean FROM product WHERE category_name = :categoryName);", nativeQuery = true)
    Optional<List<Waste>> findAllWasteOfOneCategoryFromGroup(@Param("groupId") long groupId,
                                                             @Param("categoryName") String categoryName);

    @Query(value = "SELECT waste_id FROM wastes ORDER BY waste_id ASC",nativeQuery = true)
    long getLastWaste();

}
