package ntnu.idatt2016.v233.SmartMat.controller.product;

import ntnu.idatt2016.v233.SmartMat.controller.product.AllergyController;
import ntnu.idatt2016.v233.SmartMat.dto.response.product.AllergyResponse;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import ntnu.idatt2016.v233.SmartMat.service.product.AllergyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AllergyControllerTest {

    @InjectMocks
    private AllergyController allergyController;

    @Mock
    private AllergyService allergyService;

    private Allergy allergy;
    private List<AllergyResponse> allergyResponseList;

    @BeforeEach
    public void setUp() {
        allergy = new Allergy();
        // Set properties for the allergy object

        AllergyResponse allergyResponse1 = new AllergyResponse();
        AllergyResponse allergyResponse2 = new AllergyResponse();
        // Set properties for allergyResponse1 and allergyResponse2
        allergyResponseList = Arrays.asList(allergyResponse1, allergyResponse2);
    }

    @Test
    public void getAllergyByName_found() {
        String name = "testAllergy";
        when(allergyService.getAllergyByName(name)).thenReturn(Optional.of(allergy));

        ResponseEntity<Allergy> response = allergyController.getAllergyByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allergy, response.getBody());
    }

    @Test
    public void getAllergyByName_notFound() {
        String name = "testAllergy";
        when(allergyService.getAllergyByName(name)).thenReturn(Optional.empty());

        ResponseEntity<Allergy> response = allergyController.getAllergyByName(name);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllAllergies_found() {
        when(allergyService.getAllAllergies()).thenReturn(allergyResponseList);

        ResponseEntity<List<AllergyResponse>> response = allergyController.getAllAllergies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allergyResponseList, response.getBody());
    }

    @Test
    public void getAllAllergies_notFound() {
        when(allergyService.getAllAllergies()).thenReturn(List.of());

        ResponseEntity<List<AllergyResponse>> response = allergyController.getAllAllergies();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
