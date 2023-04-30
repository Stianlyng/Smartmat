package ntnu.idatt2016.v233.SmartMat.repository.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ntnu.idatt2016.v233.SmartMat.entity.Waste;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class WasteRepositoryTest {

    @Autowired
    private WasteRepository wasteRepository;

    private Waste waste1;
    private Waste waste2;

    /**
    @BeforeEach
    void setUp() {
        waste1 = Waste.builder()
                .groupId(1L)
                .ean(123456L)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .amount(10)
                .unit("kg")
                .build();

        waste2 = Waste.builder()
                .groupId(1L)
                .ean(789012L)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .amount(5)
                .unit("kg")
                .build();

        wasteRepository.save(waste1);
        wasteRepository.save(waste2);
    }

    @Test
    void findByGroupId() {
        Optional<List<Waste>> result = wasteRepository.findByGroupId(1L);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals(waste1.getEan(), result.get().get(0).getEan());
        assertEquals(waste2.getEan(), result.get().get(1).getEan());
    }*/
}
