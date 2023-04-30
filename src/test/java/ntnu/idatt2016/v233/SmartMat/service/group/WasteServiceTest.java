package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class WasteServiceTest {
    private WasteService wasteService;

    @Mock
    private WasteRepository wasteRepository;

    /**
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wasteService = new WasteService(wasteRepository);
    }



    @Test
    public void testCreateWaste() {
        Waste waste = Waste.builder()
                .groupId(1L)
                .ean(1234567890123L)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .amount(1.0)
                .unit("kg")
                .build();

        Waste createdWaste = Waste.builder()
                .wasteId(1L)
                .groupId(1L)
                .ean(1234567890123L)
                .timestamp(waste.getTimestamp())
                .amount(1.0)
                .unit("kg")
                .build();

        when(wasteRepository.save(waste)).thenReturn(createdWaste);

        Waste result = wasteService.createWaste(waste);

        assertEquals(createdWaste, result);
    }

    @Test
    public void testGetWasteById() {
        Waste waste = Waste.builder()
                .wasteId(1L)
                .groupId(1L)
                .ean(1234567890123L)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .amount(1.0)
                .unit("kg")
                .build();

        when(wasteRepository.findById(1L)).thenReturn(Optional.of(waste));

        Optional<Waste> result = wasteService.getWasteById(1L);

        assertTrue(result.isPresent());
        assertEquals(waste, result.get());
    }
    */
}
