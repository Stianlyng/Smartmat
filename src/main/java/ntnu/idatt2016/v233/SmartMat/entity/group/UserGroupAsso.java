package ntnu.idatt2016.v233.SmartMat.entity.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

/**
 * UserGroupTable is a class representing the user_group table in the database.
 * It is used to represent the many-to-many relationship between users and groups.
 * @Author Birk, Pedro
 * @Version 1.2
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Table(name = "user_group")
public class UserGroupAsso {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("group_id")
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties({"user", "fridge", "shoppingList"})
    private Group group;

    @Column(name = "primary_group")
    private Boolean primaryGroup;

    @Column(name ="group_authority")
    private String groupAuthority;
}
