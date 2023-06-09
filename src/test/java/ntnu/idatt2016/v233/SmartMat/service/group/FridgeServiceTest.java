package ntnu.idatt2016.v233.SmartMat.service.group;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ntnu.idatt2016.v233.SmartMat.dto.request.product.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupId;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.FridgeProductAssoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;

import java.util.ArrayList;
import java.util.Optional;


public class FridgeServiceTest {

    @Mock
    private FridgeRepository fridgeRepository;

    @Mock
    private ProductService productService;


    @Mock
    private GroupRepository groupRepository;


    @Mock
    private WasteRepository wasteRepository;

    @Mock
    private FridgeProductAssoRepo fridgeProductAssoRepo;

    @InjectMocks
    private FridgeService fridgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testAddProductToFridge() {
        // Arrange
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
        when(fridgeRepository.findByGroupGroupId(group.getGroupId())).thenReturn(Optional.of(fridge));

        FridgeProductRequest fridgeProductRequest = new FridgeProductRequest(12093812L, group.getGroupId(),
                product.get().getEan(), 1, 10, 100);

        // Act
        Optional<Product> result = fridgeService.addProductToFridge(fridgeProductRequest);


        verify(fridgeRepository).save(any(Fridge.class));


        // Assert
        assertTrue(result.isPresent());

        assertTrue(fridge.getProducts().stream().anyMatch(fp -> fp.getEan().getEan() == product.get().getEan()));
    }



    @Test
    void testGetFridgeByGroupId() {
        // Arrange
        long groupId = 12345L;
        Fridge fridge = new Fridge();
        fridge.setGroup(Group.builder()
                        .groupId(groupId)
                .build());
        when(fridgeRepository.findByGroupGroupId(groupId)).thenReturn(Optional.of(fridge));

        // Act
        Optional<Fridge> result = fridgeService.getFridgeByGroupId(groupId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(groupId, result.get().getGroup().getGroupId());
        verify(fridgeRepository).findByGroupGroupId(groupId);
    }

    @Test
    void testGetFridgeByGroupIdNotFound() {
        // Arrange
        long groupId = 12345L;
        when(fridgeRepository.findByGroupGroupId(groupId)).thenReturn(Optional.empty());

        // Act
        Optional<Fridge> result = fridgeService.getFridgeByGroupId(groupId);

        // Assert
        assertTrue(result.isEmpty());
        verify(fridgeRepository).findByGroupGroupId(groupId);
    }

    @Test
    void testRemoveItemFromFridge(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                        .ean(ean)
                        .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));
        when(productService.getProductById(ean)).thenReturn(Optional.of(product));

        // Act
        boolean result = fridgeService.removeProductFromFridge(123456L);

        // Assert
        assertTrue(result);
        verify(fridgeProductAssoRepo).delete(fridgepr);
    }

    @Test
    void testDeleteAmmountFromFridge(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                        .ean(ean)
                        .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));
        when(fridgeProductAssoRepo.findAllById(123456L)).thenReturn(Optional.of(fridgepr));
        when(productService.getProductById(ean)).thenReturn(Optional.of(product));

        double newAmount = fridgepr.getAmount() - 1;


        when(fridgeProductAssoRepo.save(FridgeProductAsso.builder()
                .amount(newAmount)
                .id(123456L)
                .ean(product)
                .fridgeId(fridge)
                .build()
        )).thenReturn(FridgeProductAsso.builder()
                        .amount(newAmount)
                        .id(139132L)
                        .ean(product)
                        .fridgeId(fridge)
                .build());

        // Act
        Optional<FridgeProductAsso> result = fridgeService.deleteAmountFromFridge(123456L, 1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(newAmount, result.get().getAmount());
        assertNotEquals(2, result.get().getAmount());

        verify(fridgeProductAssoRepo).save(any(FridgeProductAsso.class));
    }


    @Test
    void deleteEntireAmountFromProduct(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                .ean(ean)
                .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));
        when(fridgeProductAssoRepo.findAllById(123456L)).thenReturn(Optional.of(fridgepr));
        when(productService.getProductById(ean)).thenReturn(Optional.of(product));

        double newAmount = fridgepr.getAmount() - 1;


        when(fridgeProductAssoRepo.save(FridgeProductAsso.builder()
                .amount(newAmount)
                .id(123456L)
                .ean(product)
                .fridgeId(fridge)
                .build()
        )).thenReturn(FridgeProductAsso.builder()
                .amount(newAmount)
                .id(139132L)
                .ean(product)
                .fridgeId(fridge)
                .build());

        // Act
        Optional<FridgeProductAsso> result = fridgeService.deleteAmountFromFridge(123456L, 2);

        // Assert
        assertTrue(result.isEmpty());

        verify(fridgeProductAssoRepo).delete(any(FridgeProductAsso.class));

    }

    @Test
    void wasteProductFromFridge(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                .ean(ean)
                .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        group.setPoints(100);
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));
        when(fridgeProductAssoRepo.findAllById(123456L)).thenReturn(Optional.of(fridgepr));
        when(productService.getProductById(ean)).thenReturn(Optional.of(product));
        when(wasteRepository.save(any(Waste.class))).thenReturn(Waste.builder()
                .amount(2)
                .ean(product)
                .groupId(group)
                .build());

        // Act

        Optional<Waste> result = fridgeService.wasteProductFromFridge(123456L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getAmount());
        assertEquals(product.getEan(), result.get().getEan().getEan());
        assertEquals(group.getGroupId(), result.get().getGroupId().getGroupId());
        assertEquals(group.getPoints(), 99);

        verify(fridgeProductAssoRepo).delete(any(FridgeProductAsso.class));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void updateProductInFridge(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                .ean(ean)
                .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));
        when(productService.getProductById(ean)).thenReturn(Optional.of(product));

        when(fridgeProductAssoRepo.save(any(FridgeProductAsso.class))).thenReturn(FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(1)
                .daysToExpiration(100)
                .buyPrice(100.0)
                .build());
        // Act

        FridgeProductRequest fridgeProductRequest = new FridgeProductRequest(123456L, group.getGroupId(),
                product.getEan(), 1, 100, 100);

        Optional<FridgeProductAsso> result = fridgeService.updateProductInFridge(fridgeProductRequest);

        // Assert

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getAmount());
        assertEquals(100, result.get().getDaysToExpiration());
        assertEquals(100, result.get().getBuyPrice());

        verify(fridgeProductAssoRepo).save(any(FridgeProductAsso.class));

    }

    @Test
    void isUserAssosiatedWithFridge(){
        Fridge fridge = new Fridge();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        User user = User.builder()
                .username("test")
                .password("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                .user(user)
                .group(group)
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);

        when(fridgeRepository.findById(fridge.getFridgeId())).thenReturn(Optional.of(fridge));

        // Act

        boolean result = fridgeService.isUserInFridge(user.getUsername(), fridge.getFridgeId());

        // Assert

        assertTrue(result);
        verify(fridgeRepository).findById(fridge.getFridgeId());

    }

    @Test
    void isUserNotAssosiatedWithFridge(){
        Fridge fridge = new Fridge();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        User user = User.builder()
                .username("test")
                .password("test")
                .build();

        when(fridgeRepository.findById(fridge.getFridgeId())).thenReturn(Optional.of(fridge));

        // Act

        boolean result = fridgeService.isUserInFridge(user.getUsername(), fridge.getFridgeId());

        // Assert

        assertFalse(result);
        verify(fridgeRepository).findById(fridge.getFridgeId());

    }

    @Test
    void getFridgeByFridgeId(){
        // Arrange
        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        when(fridgeRepository.findById(1234L)).thenReturn(Optional.of(fridge));

        // Act

        Optional<Fridge> result = fridgeService.getFridgeByFridgeId(1234L);

        // Assert

        assertTrue(result.isPresent());
        assertEquals(1234L, result.get().getFridgeId());

        verify(fridgeRepository).findById(1234L);
    }

    @Test
    void isUserInGroupWithFridgeProduct(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                .ean(ean)
                .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        User user = User.builder()
                .username("test")
                .password("test")
                .build();

        UserGroupAsso userGroupAsso = UserGroupAsso.builder()
                .id(new UserGroupId(user.getUsername(), group.getGroupId()))
                .user(user)
                .group(group)
                .build();

        user.addGroup(userGroupAsso);
        group.addUser(userGroupAsso);

        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));

        // Act

        boolean result = fridgeService.isUserInGroupWithFridgeProduct(user.getUsername(), 123456L);

        // Assert

        assertTrue(result);
        verify(fridgeProductAssoRepo).findById(123456L);
    }


    @Test
    void isUserInGroupWithFridgeProduct_not(){
        // Arrange
        long ean = 12345L;
        Product product = Product.builder()
                .ean(ean)
                .name("Test Product")
                .build();

        Fridge fridge = Fridge.builder()
                .fridgeId(1234L)
                .build();
        fridge.setProducts(new ArrayList<>());
        Group group = new Group();
        fridge.setGroup(group);
        group.setFridge(fridge);

        FridgeProductAsso fridgepr = FridgeProductAsso.builder()
                .fridgeId(fridge)
                .ean(product)
                .id(123456L)
                .amount(2)
                .build();

        fridge.addProduct(fridgepr);
        product.addFridge(fridgepr);

        User user = User.builder()
                .username("test")
                .password("test")
                .build();


        when(fridgeProductAssoRepo.findById(123456L)).thenReturn(Optional.of(fridgepr));

        // Act

        boolean result = fridgeService.isUserInGroupWithFridgeProduct(user.getUsername(), 123456L);

        // Assert

        assertFalse(result);
        verify(fridgeProductAssoRepo).findById(123456L);
    }


}
