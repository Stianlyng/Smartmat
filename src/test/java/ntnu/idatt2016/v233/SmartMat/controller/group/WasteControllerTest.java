package ntnu.idatt2016.v233.SmartMat.controller.group;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.dto.request.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.WasteService;
import ntnu.idatt2016.v233.SmartMat.util.CategoryUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WasteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WasteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WasteService wasteService;

    private Group group;
    private Product product;
    private WasteRequest wasteRequest;
    private Waste expectedWaste;

    @BeforeEach
    public void setUp() {
        group = new Group();
        group.setGroupId(1L);
        group.setGroupName("TestGroup");

        product = new Product();
        product.setEan(12345678L);

        wasteRequest = new WasteRequest(1L, 12345678L, 0.5, "kg");

        expectedWaste = new Waste();
        expectedWaste.setWasteId(1L);
        expectedWaste.setGroupId(group);
        expectedWaste.setEan(product);
        expectedWaste.setTimestamp(new Timestamp(System.currentTimeMillis()));
        expectedWaste.setAmount(0.5);
        expectedWaste.setUnit("kg");
    }

    // Test cases go here
    @Test
    public void testCreateWaste() throws Exception {
        when(wasteService.createWaste(wasteRequest)).thenReturn(Optional.of(expectedWaste));

        mockMvc.perform(post("/api/waste/waste")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wasteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("wasteId").value(expectedWaste.getWasteId()));

        verify(wasteService, times(1)).createWaste(wasteRequest);
    }

    @Test
    public void testGetWasteOfCategoryByGroupId() throws Exception {
        int categoryNumber = 1;
        String categoryName = CategoryUtil.getCategoryName(categoryNumber);
        List<Waste> expectedWastes = Arrays.asList(new Waste(/*...*/), new Waste(/*...*/));

        when(wasteService.getWasteOfCategoryByGroupId(group.getGroupId(), categoryName)).thenReturn(Optional.of(expectedWastes));

        mockMvc.perform(get("/api/waste/group/{groupId}/category/{categoryNumber}", group.getGroupId(), categoryNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].wasteId").value(expectedWastes.get(0).getWasteId()))
                .andExpect(jsonPath("$[1].wasteId").value(expectedWastes.get(1).getWasteId()));

        verify(wasteService, times(1)).getWasteOfCategoryByGroupId(group.getGroupId(), categoryName);
    }

    @Test
    public void testGetInformationOfCakeGraph() throws Exception {
        double[] expectedData = new double[]{0.3, 0.2, 0.5};

        when(wasteService.getCakeDiagram(group.getGroupId())).thenReturn(Optional.of(expectedData));

        mockMvc.perform(get("/api/waste/statistic/cakeGraph/{groupId}", group.getGroupId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]").value(expectedData[0]))
                .andExpect(jsonPath("$[1]").value(expectedData[1]))
                .andExpect(jsonPath("$[2]").value(expectedData[2]));

        verify(wasteService, times(1)).getCakeDiagram(group.getGroupId());
    }

    @Test
    public void testGetWasteById_NotFound() throws Exception {
        long nonExistingWasteId = 99L;

        when(wasteService.getWasteById(nonExistingWasteId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/waste/{wasteId}", nonExistingWasteId))
                .andExpect(status().isNotFound());

        verify(wasteService, times(1)).getWasteById(nonExistingWasteId);
    }
}
