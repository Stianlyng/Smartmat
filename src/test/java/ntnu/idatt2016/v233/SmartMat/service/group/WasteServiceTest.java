package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.dto.request.group.WasteRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.product.Category;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WasteServiceTest {
    @InjectMocks
    private WasteService wasteService;

    @Mock
    private WasteRepository wasteRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ProductRepository productRepository;

    public Group group;
    public Product product;
    public Waste waste;
    public WasteRequest wasteRequest;

    @BeforeEach
    public void setUp() {
        Category category = Category.builder().categoryName("Test").build();
        group = Group.builder().groupId(1).build();
        product = Product.builder().ean(1).amount(2.0).unit("kg").category(category).build();
        waste = Waste.builder().wasteId(1).groupId(group).ean(product).amount(1).unit("kg").timestamp(new Timestamp(System.currentTimeMillis())).build();
        wasteRequest = new WasteRequest(1, 1, 1, "kg");
    }

    @Test
    void createWaste_Success() {
        when(groupRepository.findByGroupId(wasteRequest.groupId())).thenReturn(Optional.of(group));
        when(productRepository.findById(wasteRequest.ean())).thenReturn(Optional.of(product));
        when(wasteRepository.save(any(Waste.class))).thenReturn(waste);

        Optional<Waste> createdWaste = wasteService.createWaste(wasteRequest);

        assertTrue(createdWaste.isPresent());
        assertEquals(waste, createdWaste.get());
    }

    @Test
    void createWaste_Failure() {
        when(groupRepository.findByGroupId(wasteRequest.groupId())).thenReturn(Optional.empty());
        when(productRepository.findById(wasteRequest.ean())).thenReturn(Optional.empty());

        Optional<Waste> createdWaste = wasteService.createWaste(wasteRequest);

        assertFalse(createdWaste.isPresent());
    }

    @Test
    void getWasteById_Success() {
        when(wasteRepository.findById(waste.getWasteId())).thenReturn(Optional.of(waste));

        Optional<Waste> fetchedWaste = wasteService.getWasteById(waste.getWasteId());

        assertTrue(fetchedWaste.isPresent());
        assertEquals(waste, fetchedWaste.get());
    }

    @Test
    void getWasteById_Failure() {
        when(wasteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Waste> fetchedWaste = wasteService.getWasteById(999L);

        assertFalse(fetchedWaste.isPresent());
    }

    @Test
    void getWasteByGroupId_Success() {
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));
        when(wasteRepository.findByGroupId(group)).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<List<Waste>> fetchedWastes = wasteService.getWasteByGroupId(group.getGroupId());

        assertTrue(fetchedWastes.isPresent());
        assertEquals(1, fetchedWastes.get().size());
        assertEquals(waste, fetchedWastes.get().get(0));
    }

    @Test
    void getWasteByGroupId_Failure() {
        when(groupRepository.findByGroupId(anyLong())).thenReturn(Optional.empty());

        Optional<List<Waste>> fetchedWastes = wasteService.getWasteByGroupId(999L);

        assertFalse(fetchedWastes.isPresent());
    }

    @Test
    void getWasteOfCategoryByGroupId_Success() {
        when(wasteRepository.findAllWasteOfOneCategoryFromGroup(group.getGroupId(), product.getCategory().getCategoryName())).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<List<Waste>> fetchedWastes = wasteService.getWasteOfCategoryByGroupId(group.getGroupId(), product.getCategory().getCategoryName());

        assertTrue(fetchedWastes.isPresent());
        assertEquals(1, fetchedWastes.get().size());
        assertEquals(waste, fetchedWastes.get().get(0));
    }

    @Test
    void getWasteOfCategoryByGroupId_Failure() {
        when(wasteRepository.findAllWasteOfOneCategoryFromGroup(anyLong(), anyString())).thenReturn(Optional.empty());

        Optional<List<Waste>> fetchedWastes = wasteService.getWasteOfCategoryByGroupId(999L, "Nonexistent Category");

        assertFalse(fetchedWastes.isPresent());
    }

    @Test
    void getCakeDiagram_Success() {
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));
        when(wasteRepository.findByGroupId(group)).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<double[]> fetchedCakeDiagram = wasteService.getCakeDiagram(group.getGroupId());

        assertTrue(fetchedCakeDiagram.isPresent());
    }

    @Test
    void getCakeDiagram_Failure() {
        when(groupRepository.findByGroupId(anyLong())).thenReturn(Optional.empty());

        Optional<double[]> fetchedCakeDiagram = wasteService.getCakeDiagram(999L);

        assertFalse(fetchedCakeDiagram.isPresent());
    }

    @Test
    void getLastMonth_Success() {
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));
        when(wasteRepository.findByGroupId(group)).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<double[]> fetchedLastMonthData = wasteService.getLastMonth(group.getGroupId());

        assertTrue(fetchedLastMonthData.isPresent());
    }

    @Test
    void getLastMonth_Failure() {
        when(groupRepository.findByGroupId(anyLong())).thenReturn(Optional.empty());

        Optional<double[]> fetchedLastMonthData = wasteService.getLastMonth(999L);

        assertFalse(fetchedLastMonthData.isPresent());
    }

    @Test
    void getLostMoney_Success() {
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));
        when(wasteRepository.findByGroupId(group)).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<Double> fetchedLostMoney = wasteService.getLostMoney(group.getGroupId());

        assertTrue(fetchedLostMoney.isPresent());
    }

    @Test
    void getLostMoney_Failure() {
        when(groupRepository.findByGroupId(anyLong())).thenReturn(Optional.empty());

        Optional<Double> fetchedLostMoney = wasteService.getLostMoney(999L);

        assertFalse(fetchedLostMoney.isPresent());
    }

    @Test
    void getCO2PerPerson_Success() {
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(Optional.of(group));
        when(groupRepository.countAllUserInGroup(group.getGroupId())).thenReturn(1);
        when(wasteRepository.findByGroupId(group)).thenReturn(Optional.of(Collections.singletonList(waste)));

        Optional<Double> fetchedCO2PerPerson = wasteService.getCO2PerPerson(group.getGroupId());

        assertTrue(fetchedCO2PerPerson.isPresent());
    }

    @Test
    void getCO2PerPerson_Failure() {
        when(groupRepository.findByGroupId(anyLong())).thenReturn(Optional.empty());

        Optional<Double> fetchedCO2PerPerson = wasteService.getCO2PerPerson(999L);

        assertFalse(fetchedCO2PerPerson.isPresent());
    }


    @Test
    void isUserAssosiatedWithWaste_Failure() {
        when(wasteRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isUserAssociated = wasteService.isUserAssosiatedWithWaste("TestUsername", 999L);

        assertFalse(isUserAssociated);
    }

}