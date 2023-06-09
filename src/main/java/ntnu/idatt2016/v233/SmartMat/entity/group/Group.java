package ntnu.idatt2016.v233.SmartMat.entity.group;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Group is an entity class representing a user group in the system.
 *
 * @author Stian Lyng, Anders Austlid, Birk, Pedro
 * @version 1.3
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


    @OneToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY, mappedBy = "group",
            orphanRemoval = true
    )
    @JsonIgnoreProperties("group")
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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY,
            mappedBy = "groups")
    @JsonIgnoreProperties({"groups"})
    private List<Achievement> achievements;

    /**
     * Checks if two objects are equal
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Group group)){
            return false;
        }
        return group.getGroupId() == this.getGroupId();
    }

    /**
     * Gets the hashcode of the object
     * @return the hashcode of the object
     */
    @Override
    public int hashCode(){
        return Long.hashCode(this.getGroupId());
    }

}
