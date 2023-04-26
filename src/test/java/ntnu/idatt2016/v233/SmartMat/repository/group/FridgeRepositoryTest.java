package ntnu.idatt2016.v233.SmartMat.repository.group;

import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
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
    @BeforeEach
    void setUp() {
        fridge = new Fridge();
        fridge.setGroupId(1L);

        entityManager.persist(fridge);
    }


    @Test
    void shouldFindByGroupId() {
        assertEquals(fridge, fridgeRepository.findByGroupId(fridge.getGroupId()).get());
    }

    @Test
    void shouldNotFindByGroupId() {
        assertFalse(fridgeRepository.findByGroupId(2L).isPresent());
    }

    @Test
    void shouldSave() {
        Fridge fridge2 = new Fridge();
        fridge2.setGroupId(2L);
        fridgeRepository.save(fridge2);
        assertEquals(fridge2, fridgeRepository.findByGroupId(fridge2.getGroupId()).get());

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