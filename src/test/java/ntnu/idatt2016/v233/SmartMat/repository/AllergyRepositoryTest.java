package ntnu.idatt2016.v233.SmartMat.repository;

import ntnu.idatt2016.v233.SmartMat.entity.Allergy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("AllergyRepositoryTest")
class AllergyRepositoryTest {

    @Autowired
    private AllergyRepository allergyRepository;

    @Test
    @DisplayName("Test findByName")
    void testFindByName() {
        Allergy allergy = Allergy.builder()
                .name("testName")
                .description("testDescription")
                .build();
        allergyRepository.save(allergy);

        Assertions.assertEquals(1, allergyRepository.findAll().size());
        Optional<Allergy> allergies = allergyRepository.findById("testName");
        Assertions.assertTrue(allergies.isPresent());
        Assertions.assertEquals("testName", allergies.get().getName());
    }

    @Test
    @DisplayName("Test save")
    void testSave() {
        Allergy allergy = Allergy.builder()
                .name("testName")
                .description("testDescription")
                .build();

        Allergy savedAllergy = allergyRepository.save(allergy);

        Assertions.assertEquals(allergy, savedAllergy);
        Assertions.assertEquals(1, allergyRepository.findAll().size());

    }

    @Test
    @DisplayName("Test delete")
    void testDelete() {
        Allergy allergy = Allergy.builder()
                .name("testName")
                .description("testDescription")
                .build();
        allergyRepository.save(allergy);

        allergyRepository.deleteById("testName");
        Optional<Allergy> deletedAllergy = allergyRepository.findById("testName");

        Assertions.assertTrue(deletedAllergy.isEmpty());
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        // Arrange
        Allergy allergy1 = Allergy.builder()
                .name("testName")
                .description("testDescription")
                .build();
        Allergy allergy2 = Allergy.builder()
                .name("testName2")
                .description("testDescription2")
                .build();
        allergyRepository.save(allergy1);
        allergyRepository.save(allergy2);

        // Act
        List<Allergy> allergies = allergyRepository.findAll();

        // Assert
        Assertions.assertEquals(2, allergies.size());
        Assertions.assertTrue(allergies.contains(allergy1));
        Assertions.assertTrue(allergies.contains(allergy2));
    }
}
