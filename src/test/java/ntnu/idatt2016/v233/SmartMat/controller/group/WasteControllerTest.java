package ntnu.idatt2016.v233.SmartMat.controller.group;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WasteControllerTest {

    @InjectMocks
    private WasteController wasteController;

    @Mock
    private WasteService wasteService;

    private Waste waste;
/**
    @BeforeEach
    public void setUp() {
        waste = Waste.builder()
                .wasteId(1L)
                .groupId(1L)
                .ean(123456789L)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .amount(5.5)
                .unit("kg")
                .build();
    }

    @Test
    public void testCreateWaste() {
        when(wasteService.getWasteById(waste.getWasteId())).thenReturn(Optional.empty());
        when(wasteService.createWaste(any(Waste.class))).thenReturn(waste);

        ResponseEntity<Waste> response = wasteController.createWaste(waste);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waste, response.getBody());

        verify(wasteService, times(1)).getWasteById(waste.getWasteId());
        verify(wasteService, times(1)).createWaste(any(Waste.class));
    }

    @Test
    public void testCreateWaste_badRequest() {
        when(wasteService.getWasteById(waste.getWasteId())).thenReturn(Optional.of(waste));

        ResponseEntity<Waste> response = wasteController.createWaste(waste);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(wasteService, times(1)).getWasteById(waste.getWasteId());
        verify(wasteService, never()).createWaste(any(Waste.class));
    }

    @Test
    public void testGetWasteById_found() {
        when(wasteService.getWasteById(waste.getWasteId())).thenReturn(Optional.of(waste));

        ResponseEntity<Waste> response = wasteController.getWasteById(waste.getWasteId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waste, response.getBody());

        verify(wasteService, times(1)).getWasteById(waste.getWasteId());
    }

    @Test
    public void testGetWasteById_notFound() {
        when(wasteService.getWasteById(waste.getWasteId())).thenReturn(Optional.empty());

        ResponseEntity<Waste> response = wasteController.getWasteById(waste.getWasteId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(wasteService, times(1)).getWasteById(waste.getWasteId());
    }
    */
}