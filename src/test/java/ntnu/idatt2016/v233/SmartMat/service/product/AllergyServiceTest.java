package ntnu.idatt2016.v233.SmartMat.service.product;

import ntnu.idatt2016.v233.SmartMat.service.product.AllergyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.dto.response.product.AllergyResponse;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.repository.product.AllergyRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AllergyServiceTest {

    @InjectMocks
    private AllergyService allergyService;

    @Mock
    private AllergyRepository allergyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAllergies() {
        Allergy allergy1 = new Allergy("Allergy1", "Description1", null, null);
        Allergy allergy2 = new Allergy("Allergy2", "Description2", null, null);

        when(allergyRepository.findAll()).thenReturn(Arrays.asList(allergy1, allergy2));

        List<AllergyResponse> result = allergyService.getAllAllergies();

        assertEquals(2, result.size());
        assertEquals("Allergy1", result.get(0).getName());
        assertEquals("Description1", result.get(0).getDescription());
        assertEquals("Allergy2", result.get(1).getName());
        assertEquals("Description2", result.get(1).getDescription());
    }

    @Test
    void testGetAllergyByName() {
        Allergy allergy = new Allergy("Allergy1", "Description1", null, null);

        when(allergyRepository.findById("Allergy1")).thenReturn(Optional.of(allergy));

        Optional<Allergy> result = allergyService.getAllergyByName("Allergy1");

        assertTrue(result.isPresent());
        assertEquals("Allergy1", result.get().getName());
        assertEquals("Description1", result.get().getDescription());
    }

    @Test
    void testSaveAllergy() {
        Allergy allergy = new Allergy("Allergy1", "Description1", null, null);

        allergyService.saveAllergy(allergy);

        verify(allergyRepository, times(1)).save(allergy);
    }

    @Test
    void testDeleteAllergyByName() {
        allergyService.deleteAllergyByName("Allergy1");

        verify(allergyRepository, times(1)).deleteById("Allergy1");
    }
}
