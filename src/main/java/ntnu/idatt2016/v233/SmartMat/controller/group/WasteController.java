package ntnu.idatt2016.v233.SmartMat.controller.group;

import java.util.List;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/waste")
public class WasteController {

    private final WasteService wasteService;

    /**
     * Saves a new waste
     *
     * @param waste the waste to save
     * @return a ResponseEntity containing the saved waste if it was saved successfully, or a 400 if it wasn't
     */
    @PostMapping("/waste")
    public ResponseEntity<Waste> createWaste(@RequestBody WasteRequest waste) {
        return wasteService.createWaste(waste).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    /**
     * Gets a waste by its id
     *
     * @param wasteId the id of the waste
     * @return a ResponseEntity containing the waste if it exists, or a 404 if it doesn't
     */
    @GetMapping("/{wasteId}")
    public ResponseEntity<Waste> getWasteById(@PathVariable("wasteId") long wasteId) {
        return wasteService.getWasteById(wasteId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets a waste by its group id
     *
     * @param groupId the id of the group
     * @return a ResponseEntity containing the waste if it exists, or a 404 if it doesn't
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Waste>> getWasteByGroupId(@PathVariable("groupId") long groupId) {
        return wasteService.getWasteByGroupId(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Retrieves a list of Waste objects of a specific category from a group
     *
     * @param groupId the ID of the group to search for
     * @param categoryNumber the number representing the category to search for
     * @return a ResponseEntity containing a list of Waste objects if successful, or a not found ResponseEntity if the data is not found
     */
    @GetMapping("/group/{groupId}/category/{categoryNumber}")
    public ResponseEntity<List<Waste>> getWasteOfCategoryByGroupId(@PathVariable("groupId") long groupId,
                                                                   @PathVariable("categoryNumber")  int categoryNumber){
        return wasteService.getWasteOfCategoryByGroupId(groupId, CategoryUtil.getCategoryName(categoryNumber)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve information about the cake diagram for a given group ID using a GET request.
     *
     * @param groupId The ID of the group for which to retrieve the cake diagram information.
     * @return A ResponseEntity containing an array of doubles representing the cake diagram information if found,
     *         or a 404 Not Found response if not found.
     */
    @GetMapping("/statistic/cakeGraph/{groupId}")
    public ResponseEntity<double[]> getInformationOfCakeGraph(@PathVariable("groupId") long groupId){
        return wasteService.getCakeDiagram(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }


    /**
     * Get the information of the last months of a specific group.
     *
     * @param groupId the id of the group to get the information for
     * @return a ResponseEntity object containing an array of doubles representing the waste for each category
     * in the last four months, or a not found response if the group does not exist or has no waste data
     */
    @GetMapping("/statistic/lastMonths/{groupId}")
    public ResponseEntity<double[]> getInformationOfLastMoths(@PathVariable("groupId") long groupId){
        return wasteService.getLastMonth(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the amount of money lost due to expired products in a specific group.
     * The amount is calculated based on the total cost of the expired products.
     *
     * @param groupId the ID of the group to retrieve the lost money from
     * @return a ResponseEntity with the lost money as a Double if found, or a ResponseEntity with status 404 if the group is not found
     */
    @GetMapping("/statistic/lostMoney/{groupId}")
    public ResponseEntity<Double> getLostMoney(@PathVariable("groupId") long groupId){
        return wasteService.getLostMoney(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the amount of CO2 emitted annually per person in a specific group.
     *
     * @param groupId the ID of the group to retrieve the statistic for
     * @return a ResponseEntity containing the amount of CO2 emitted annually per person in the group,
     *         or a ResponseEntity with HTTP status 404 (not found) if the group or data is not found
     */
    @GetMapping("/statistic/annuallyCO2/{groupId}")
    public ResponseEntity<Double> getCO2Annually(@PathVariable("groupId") long groupId){
        return wasteService.getCO2PerPerson(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
