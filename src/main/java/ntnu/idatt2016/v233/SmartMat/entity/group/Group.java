package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    long groupId;

    @Column(name = "level")
    int level;

    @Column(name = "points")
    int points;
    
    @Column(name = "group_name")
    String groupName;

    @ManyToMany
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    @JsonIgnoreProperties({"groups"})
    private List<User> users;


    @ManyToMany
    @JoinTable(name = "group_achievement",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    @JsonIgnoreProperties({"groups"})
    private List<Achievement> achievements;

}
