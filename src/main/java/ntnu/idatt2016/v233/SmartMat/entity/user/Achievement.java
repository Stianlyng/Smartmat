package ntnu.idatt2016.v233.SmartMat.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Achievement is an entity class representing an achievement in the system.
 *
 * @author Anders
 * @version 1.0
 * @since 19.04.2023
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "achievement")
public class Achievement {

    @Id
    @Column(name = "achievement_name")
    private String achievementName;

    @Column(name = "achievement_description")
    private String achievementDescription;
}
