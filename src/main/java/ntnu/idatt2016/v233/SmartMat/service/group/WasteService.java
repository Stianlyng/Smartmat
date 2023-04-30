package ntnu.idatt2016.v233.SmartMat.service.group;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WasteService {
    private final WasteRepository wasteRepository;
    private final GroupRepository groupRepository;
    private final ProductRepository productRepository;

    /**
     * Creates a new waste
     *
     * @param WasteRequest the waste to create
     * @return the created waste
     */
    public Optional<Waste> createWaste(WasteRequest wasteRequest) {
        Optional<Group> group = groupRepository.findByGroupId(wasteRequest.groupId());
        Optional<Product> product = productRepository.findById(wasteRequest.ean());
        Optional<Long> wasteId = wasteRepository.findLastID();
        long id;
        id = wasteId.orElse(1L);
        if(group.isPresent() && product.isPresent()){
            return Optional.of(wasteRepository.save(Waste.builder().unit(wasteRequest.unit()).wasteId(id).timestamp(new Timestamp(System.currentTimeMillis())).amount(wasteRequest.amount()).product(product.get()).group(group.get()).build()));
        }
        return Optional.empty();
    }

    /**
     * Gets a waste by its id
     * @param id the id of the waste
     * @return an optional containing the waste if it exists
     */
    public Optional<Waste> getWasteById(long id) {
        return wasteRepository.findById(id);
    }

    /**
     * Gets a waste by its group id
     *
     * @param groupId the id of the group
     * @return an optional containing the waste if it exists
     */
    public Optional<List<Waste>> getWasteByGroupId(long groupId) {
        return wasteRepository.findByGroupId(groupId);
    }

    /**
     * Returns an Optional containing a List of Waste objects for a specific category and group id,
     * or an empty Optional if there are no wastes for that category and group id.
     *
     * @param groupId         the ID of the group to search in
     * @param categoryName    the name of the category to search for
     * @return an Optional containing a List of Waste objects, or an empty Optional if there are no wastes for that category and group id
     */
    public Optional<List<Waste>> getWasteOfCategoryByGroupId(long groupId, String categoryName){
        return wasteRepository.findAllWasteOfOneCategoryFromGroup(groupId,categoryName);
    }

}
