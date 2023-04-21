package ntnu.idatt2016.v233.SmartMat.controller.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
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
    public ResponseEntity<Waste> createWaste(@RequestBody Waste waste) {
        if(wasteService.getWasteById(waste.getWasteId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(wasteService.createWaste(waste));
    }

    /**
     * Gets a waste by its id
     *
     * @param wasteId the id of the waste
     * @return a ResponseEntity containing the waste if it exists, or a 404 if it doesn't
     */
    @GetMapping("/waste/{wasteId}")
    public ResponseEntity<Waste> getWasteById(@PathVariable("wasteId") long wasteId) {
        return wasteService.getWasteById(wasteId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
