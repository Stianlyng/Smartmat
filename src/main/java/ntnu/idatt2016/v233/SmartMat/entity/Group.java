package ntnu.idatt2016.v233.SmartMat.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Group is an entity class representing a group in the system.
 *
 * @author Stian Lyng, Anders Austlid
 * @version 1.0.1
 * @since 20.04.2023
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "groups")
@Data
public class Group {

    @Id
    @Column(name = "group_id")
    long groupId;

    @Column(name = "level")
    int level;

    @Column(name = "points")
    int points;
    
    @Column(name = "group_name")
    String groupName;
}
