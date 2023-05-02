package ntnu.idatt2016.v233.SmartMat.controller.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.group.FridgeService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FridgeController.class)
@AutoConfigureMockMvc(addFilters = false) // Disables security for this test class
public class FridgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FridgeService fridgeService;

    private Fridge fridge;
    private Product product;
    private FridgeProductAsso fridgeProductAsso;
    private FridgeProductRequest fridgeProductRequest;

    @BeforeEach
    public void setUp() {
        // Create and set up your test data here
        fridge = new Fridge();
        product = new Product();
        fridgeProductAsso = new FridgeProductAsso();
        fridgeProductRequest = new FridgeProductRequest(1L, 1L, 1L, 2, 7);
    }

    @Test
    public void getFridgeByGroupId() throws Exception {
        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.of(fridge));

        mockMvc.perform(get("/api/fridges/group/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFridgeByGroupId_notFound() throws Exception {
        when(fridgeService.getFridgeByGroupId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/fridges/group/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFridgeByFridgeId() throws Exception {
        when(fridgeService.getFridgeByFridgeId(1L)).thenReturn(Optional.of(fridge));

        mockMvc.perform(get("/api/fridges/fridge/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFridgeByFridgeId_notFound() throws Exception {
        when(fridgeService.getFridgeByFridgeId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/fridges/fridge/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addProductToFridge() throws Exception {
        when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(product));

        mockMvc.perform(post("/api/fridges/group/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridgeProductRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void addProductToFridge_notFound() throws Exception {
        when(fridgeService.addProductToFridge(any(FridgeProductRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/fridges/group/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridgeProductRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProductInFridge() throws Exception {
        when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class))).thenReturn(Optional.of(fridgeProductAsso));

        mockMvc.perform(put("/api/fridges/group/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridgeProductRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductInFridge_notFound() throws Exception {
        when(fridgeService.updateProductInFridge(any(FridgeProductRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/fridges/group/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridgeProductRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeProductFromFridge_success() throws Exception {
        when(fridgeService.removeProductFromFridge(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/fridges/delete/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void removeProductFromFridge_badRequest() throws Exception {
        when(fridgeService.removeProductFromFridge(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/fridges/delete/product/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product not found in the fridge"));
    }
}
