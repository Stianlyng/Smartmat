package ntnu.idatt2016.v233.SmartMat.service.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;


public class FridgeServiceTest {

    @Mock
    private FridgeRepository fridgeRepository;

    @Mock
    private ProductService productService;


    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private FridgeService fridgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    public void testAddProductToFridge() {
        // Arrange
        long groupId = 1L;
        long ean = 12345L;
        Optional<Product> product = Optional.of(Product.builder()
                        .ean(ean)
                        .name("Test Product")
                .build());
        Fridge fridge = new Fridge();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        when(productService.getProductById(ean)).thenReturn(product);
        when(fridgeRepository.findByGroupGroupId(groupId)).thenReturn(Optional.of(fridge));
        when(fridgeProductAssoService.createFridgeProductAsso(any(Fridge.class), any(Product.class), any(Date.class))).thenReturn(new FridgeProductAsso());

        // Act
        boolean result = fridgeService.addProductToFridge(groupId, ean);


        verify(fridgeProductAssoService).createFridgeProductAsso(any(Fridge.class), any(Product.class), any(Date.class));


        // Assert
        assertTrue(result);
    }



    @Test
    public void testAddGroupToFridge() {
        // Arrange
        long fridgeId = 1L;
        long groupId = 1L;
        Optional<Fridge> fridge = Optional.of(new Fridge());
        Optional<Group> group = Optional.of(new Group());

        when(fridgeRepository.findById(fridgeId)).thenReturn(fridge);
        when(groupRepository.findByGroupId(groupId)).thenReturn(group);

        // Act
        fridgeService.addGroupToFridge(fridgeId, groupId);

        // Assert
        assertEquals(fridge.get().getGroup(), group.get());
        assertEquals(group.get().getFridge(), fridge.get());
    }*/
}
