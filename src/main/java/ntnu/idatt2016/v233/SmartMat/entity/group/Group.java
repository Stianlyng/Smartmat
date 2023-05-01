package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.ShoppingList;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "group")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("groupId")
    private ShoppingList shoppingList;


    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("group")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserGroupAsso> user = new ArrayList<>();


    public void addUser(UserGroupAsso userGroupTable){
        if (this.user == null) {
            this.user = new ArrayList<>();
        }

        this.user.add(userGroupTable);
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
    @JsonIgnoreProperties("group")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fridge fridge;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "group_achievement",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_name"))
    @JsonIgnoreProperties({"groups"})
    private List<Achievement> achievements;

}
