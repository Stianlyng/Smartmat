package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FridgeRepositoryTest {

    @Autowired
    FridgeRepository fridgeRepository;

    @Autowired
    TestEntityManager entityManager;
    Fridge fridge;

    Group group;

    @BeforeEach
    void setUp() {
        fridge = new Fridge();

        group = new Group();

        fridge.setGroup(group);
        group.setFridge(fridge);

        entityManager.persist(group);
        entityManager.persist(fridge);
    }


    @Test
    void shouldFindByGroupId() {
        assertEquals(fridge, fridgeRepository.findByGroupGroupId(fridge.getGroup().getGroupId()).get());
    }

    @Test
    void shouldNotFindByGroupId() {
        assertFalse(fridgeRepository.findByGroupGroupId(2L).isPresent());
    }

    @Test
    void shouldSave() {
        Fridge fridge2 = new Fridge();
        Group group2 = new Group();
        entityManager.persist(group2);
        fridge2.setGroup(group2);
        group2.setFridge(fridge2);
        fridgeRepository.save(fridge2);
        assertEquals(fridge2, fridgeRepository.findByGroupGroupId(fridge2.getGroup().getGroupId()).get());

    }

    @Test
    void shouldFindAll() {
        assertEquals(1, fridgeRepository.findAll().size());
    }

    @Test
    void shouldFindById(){
        assertEquals(fridge, fridgeRepository.findById(fridge.getFridgeId()).get());
    }

    @Test
    void shouldNotFindById(){
        assertFalse(fridgeRepository.findById(2L).isPresent());
    }

    @Test
    void shouldDeleteById(){
        fridgeRepository.deleteById(fridge.getFridgeId());
        assertFalse(fridgeRepository.findById(fridge.getFridgeId()).isPresent());
    }


  
}