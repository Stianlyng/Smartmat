package ntnu.idatt2016.v233.SmartMat.service.group;

import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductId;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeProductAssoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FridgeProductAssoServiceTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    FridgeProductAssoRepository fridgeProductAssoRepository;

    private Product product;

    private Fridge fridge;
    @BeforeEach
    void setUp() {
        entityManager.clear();

        product = Product.builder()
                .ean(1234567890123L)
                .name("Test")
                .description("Test")
                .build();

        fridge = Fridge.builder()
                .build();

        FridgeProductAsso fridgeProductAsso = FridgeProductAsso.builder()
                .ean(product)
                .fridgeId(fridge)
                .purchaseDate(Date.valueOf("2023-04-24"))
                .build();

        product.addFridge(fridgeProductAsso);
        fridge.addProduct(fridgeProductAsso);

        entityManager.persist(product);
        entityManager.persist(fridge);
        entityManager.persist(fridgeProductAsso);

    }

    @Test
    void createFridgeProductAsso() {
        Optional<FridgeProductAsso> temp = fridgeProductAssoRepository.findById(
                new FridgeProductId(fridge.getFridgeId(), product.getEan(), Date.valueOf("2023-04-24")));
        assertTrue(temp.isPresent());

        assertTrue(entityManager.find(Fridge.class, fridge.getFridgeId()).getProducts().contains(temp.get()));
        assertTrue(entityManager.find(Product.class, product.getEan()).getFridges().contains(temp.get()));

        assertEquals(product.getEan(), temp.get().getEan().getEan());
        assertEquals(fridge.getFridgeId(), temp.get().getFridgeId().getFridgeId());
    }

    @Test
    void deleteFridgeProductAsso() {

        Optional<FridgeProductAsso> temp = fridgeProductAssoRepository.findById(
                new FridgeProductId(fridge.getFridgeId(), product.getEan(), Date.valueOf("2023-04-24")));
        assertTrue(temp.isPresent());

        fridgeProductAssoRepository.delete(temp.get());

        assertFalse(fridgeProductAssoRepository.findById(
                new FridgeProductId(fridge.getFridgeId(), product.getEan(), Date.valueOf("2023-04-24"))).isPresent());

        fridge.getProducts().remove(temp.get());
        product.getFridges().remove(temp.get());

        entityManager.persist(fridge);
        entityManager.persist(product);

        assertFalse(entityManager.find(Fridge.class, fridge.getFridgeId()).getProducts().contains(temp.get()));
        assertFalse(entityManager.find(Product.class, product.getEan()).getFridges().contains(temp.get()));
    }
}