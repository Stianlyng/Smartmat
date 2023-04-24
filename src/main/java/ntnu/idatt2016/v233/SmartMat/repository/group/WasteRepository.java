package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WasteRepository extends JpaRepository<Waste, Long> {
    Optional<Waste> findByGroupId(long groupId);
}
