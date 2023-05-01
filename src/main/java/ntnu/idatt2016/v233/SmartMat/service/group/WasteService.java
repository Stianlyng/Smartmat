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


    public Optional<Waste> createWaste(WasteRequest wasteRequest) {
        Optional<Group> group = groupRepository.findByGroupId(wasteRequest.groupId());
        Optional<Product> product = productRepository.findById(wasteRequest.ean());
        if(group.isPresent() && product.isPresent()){
            return Optional.of(wasteRepository.save(Waste.builder().unit(wasteRequest.unit()).timestamp(new Timestamp(System.currentTimeMillis())).amount(wasteRequest.amount()).ean(product.get()).groupId(group.get()).build()));
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
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        if(group.isPresent()) return wasteRepository.findByGroupId(group.get());
        return Optional.empty();
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
