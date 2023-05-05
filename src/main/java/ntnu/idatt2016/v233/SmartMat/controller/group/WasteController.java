package ntnu.idatt2016.v233.SmartMat.controller.group;

import java.util.List;
import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.group.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Waste;
import ntnu.idatt2016.v233.SmartMat.service.group.GroupService;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling requests related to waste
 * @author Pedro, birk
 * @version 1.1
 * @since 04.05.2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/waste")
public class WasteController {

    private final WasteService wasteService;

    private final GroupService groupService;

    /**
     * Saves a new waste
     *
     * @param waste the waste to save
     * @param authentication the authentication of the user
     * @return a ResponseEntity containing the saved waste if it was saved successfully, or a 400 if it wasn't
     * or a 403 if the user is not associated with the group
     */
    @PostMapping("/waste")
    public ResponseEntity<Waste> createWaste(@RequestBody WasteRequest waste, Authentication authentication) {
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), waste.groupId())))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return wasteService.createWaste(waste).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    /**
     * Gets a waste by its id
     *
     * @param wasteId the id of the waste
     * @param authentication the authentication of the user
     * @return a ResponseEntity containing the waste if it exists, or a 404 if it doesn or a 403 if the user is not associated with the group
     */
    @GetMapping("/{wasteId}")
    public ResponseEntity<Waste> getWasteById(@PathVariable("wasteId") long wasteId, Authentication authentication) {
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if (!wasteService.isUserAssosiatedWithWaste(authentication.getName(), wasteId))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return wasteService.getWasteById(wasteId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets a waste by its group id
     *
     * @param groupId the id of the group
     * @param authentication the authentication of the user
     * @return a ResponseEntity containing the waste if it exists, or a 404 if it doesn't or a 403 if the user is not associated with the group
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Waste>> getWasteByGroupId(@PathVariable("groupId") long groupId,
                                                         Authentication authentication) {

        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), groupId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return wasteService.getWasteByGroupId(groupId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Retrieves a list of Waste objects of a specific category from a group
     *
     * @param groupId the ID of the group to search for
     * @param categoryNumber the number representing the category to search for
     * @param authentication the authentication of the user
     * @return a ResponseEntity containing a list of Waste objects if successful, or a not found ResponseEntity if the data is not found
     * or a 403 if the user is not associated with the group
     */
    @GetMapping("/group/{groupId}/category/{categoryNumber}")
    public ResponseEntity<List<Waste>> getWasteOfCategoryByGroupId(@PathVariable("groupId") long groupId,
                                                                   @PathVariable("categoryNumber")  int categoryNumber,
                                                                   Authentication authentication){
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), groupId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return wasteService.getWasteOfCategoryByGroupId(groupId, CategoryUtil.getCategoryName(categoryNumber)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve information about the cake diagram for a given group ID using a GET request.
     *
     * @param groupId The ID of the group for which to retrieve the cake diagram information.
     * @param authentication The authentication of the user.
     * @return A ResponseEntity containing an array of doubles representing the cake diagram information if found,
     *         or a 404 Not Found response if not found. If the user is not associated with the group, a 403 Forbidden
     */
    @GetMapping("/statistic/cakeGraph/{groupId}")
    public ResponseEntity<double[]> getInformationOfCakeGraph(@PathVariable("groupId") long groupId,
                                                              Authentication authentication){
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), groupId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

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
     * @param authentication The authentication of the user.
     * @return a ResponseEntity with the lost money as a Double if found, or a ResponseEntity with status 404 if the group is not found
     */
    @GetMapping("/statistic/lostMoney/{groupId}")
    public ResponseEntity<Double> getLostMoney(@PathVariable("groupId") long groupId, Authentication authentication){
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), groupId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return wasteService.getLostMoney(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the amount of CO2 emitted annually per person in a specific group.
     *
     * @param groupId the ID of the group to retrieve the statistic for
     * @param authentication The authentication of the user.
     * @return a ResponseEntity containing the amount of CO2 emitted annually per person in the group,
     *         or a ResponseEntity with HTTP status 404 (not found) if the group or data is not found
     */
    @GetMapping("/statistic/annuallyCO2/{groupId}")
    public ResponseEntity<Double> getCO2Annually(@PathVariable("groupId") long groupId, Authentication authentication){
        if(authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ADMIN"))){
            if(!(groupService.isUserAssociatedWithGroup(authentication.getName(), groupId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return wasteService.getCO2PerPerson(groupId).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

}
