package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

import java.util.ArrayList;
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
    long level;

    @Column(name = "points")
    long points;
    
    @Column(name = "group_name")
    String groupName;

    @Column(name = "link_code")
    String linkCode;

    @Column(name = "is_open")
    Boolean open;

    @OneToMany
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("group")
    private List<UserGroupAsso> user = new ArrayList<>();


    public void addUser(UserGroupAsso userGroupTable){
        if (this.user == null) {
            this.user = new ArrayList<>();
        }

        this.user.add(userGroupTable);
    }

    @ManyToMany
    @JoinTable(name = "group_achievement",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_name"))
    @JsonIgnoreProperties({"groups"})
    private List<Achievement> achievements;

}
