package ntnu.idatt2016.v233.SmartMat.service.group;

import java.nio.channels.FileChannel;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import ntnu.idatt2016.v233.SmartMat.util.StatisticUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for waste
 * @author Pedro, Birk
 * @version 1.1
 * @since 04.05.2023
 */
@Service
@AllArgsConstructor
public class WasteService {
    private final WasteRepository wasteRepository;
    private final GroupRepository groupRepository;
    private final ProductRepository productRepository;


    /**
     * Creates a new waste
     * @param wasteRequest the waste to create
     * @return an optional containing the waste if it was created
     */
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

    /**
     * Get the cake diagram for a group of waste.
     *
     * @param groupId The ID of the group for which to retrieve the cake diagram.
     * @return An Optional containing an array of doubles representing the cake diagram for the group,
     *         or an empty Optional if the group is not found.
     */
    public Optional<double[]> getCakeDiagram(long groupId){
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        return group.map(value -> StatisticUtil.getNumberOfWasteByCategoryName(wasteRepository.findByGroupId(value).get()));
    }

    /**
     * Retrieve an optional array of doubles representing the amount of waste produced in each of the last 4 months for a given group.
     *
     * @param groupId a long representing the id of the group whose waste production statistics are to be retrieved
     * @return an optional array of doubles representing the amount of waste produced in each of the last 4 months for the given group,
     *         or an empty optional if the group could not be found or no waste was produced in the last 4 months
     */
    public Optional<double[]>  getLastMonth(long groupId) {
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        return group.map(value -> StatisticUtil.getNumberOfWasteByLastMonth(wasteRepository.findByGroupId(value).get()));
    }

    /**
     * Retrieves the lost money in the last month for the group with the given ID.
     *
     * @param groupId the ID of the group to retrieve the lost money for
     * @return an {@code Optional} containing the lost money if the group exists, or empty if it doesn't exist or there are no wastes in the last month
     */
    public Optional<Double> getLostMoney(long groupId) {
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        return group.map(value -> StatisticUtil.getLostMoneyInLastMonth(wasteRepository.findByGroupId(value).get()));
    }

    /**
     * Calculates the annual average CO2 emissions per person in the specified group.
     *
     * @param groupId the ID of the group for which to calculate CO2 emissions
     * @return an Optional containing the annual average CO2 emissions per person, or empty if the group has no users or does not exist
     */
    public Optional<Double> getCO2PerPerson(long groupId){
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        int number = groupRepository.countAllUserInGroup(groupId);
        if(number == 0 || group.isEmpty()) return Optional.empty();
        List<Waste> wastes = wasteRepository.findByGroupId(group.get()).get();
        return Optional.of(StatisticUtil.getAnnualAverage(wastes,number));
    }


    /**
     * Checks if the user is assosiated with the waste their trying to retrive
     * @param username the username of the user
     * @param wasteId the id of the waste
     * @return true if the user is associated with the waste, false otherwise
     */
    public boolean isUserAssosiatedWithWaste(String username, long wasteId) {
        Optional<Waste> waste = wasteRepository.findById(wasteId);
        return waste.map(value -> value.getGroupId().getUser().stream()
                .anyMatch(user -> user.getUser().getUsername().equals(username))).orElse(false);
    }
}
