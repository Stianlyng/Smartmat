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

}
