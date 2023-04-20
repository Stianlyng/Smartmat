package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

import java.util.List;

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

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties({"password", "group"})
    private List<User> users;

}
