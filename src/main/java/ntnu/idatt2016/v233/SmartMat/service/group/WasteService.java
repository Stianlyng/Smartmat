package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WasteService {
    private final WasteRepository wasteRepository;

    /**
     * Creates a new waste
     *
     * @param waste the waste to create
     * @return the created waste
     */
    public Waste createWaste(Waste waste) {
        return wasteRepository.save(waste);
    }

    /**
     * Gets a waste by its id
     * @param id the id of the waste
     * @return an optional containing the waste if it exists
     */
    public Optional<Waste> getWasteById(long id) {
        return wasteRepository.findById(id);
    }
}
