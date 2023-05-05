package ntnu.idatt2016.v233.SmartMat.entity.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;

import java.util.List;

/**
 * Achievement is an entity class representing an achievement in the system.
 *
 * @author Anders, Birk
 * @version 1.1
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "group_achievement",
            joinColumns = @JoinColumn(name = "achievement_name"),
            inverseJoinColumns =  @JoinColumn(name = "group_id"))
    @JsonIgnore
    private List<Group> groups;
}
