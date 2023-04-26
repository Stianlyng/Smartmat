package ntnu.idatt2016.v233.SmartMat.entity.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

/**
 * UserGroupTable is a class representing the user_group table in the database.
 * It is used to represent the many-to-many relationship between users and groups.
 * @Author Birk
 * @Version 1.0
 * @Since 25.04
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_group")
public class UserGroupAsso {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    @JsonIgnoreProperties("group")
    private User user;

    @ManyToOne
    @MapsId("group_id")
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("user")
    private Group group;

    @Column(name = "primary_group")
    private Boolean primaryGroup;

    @Column(name ="group_authority")
    private String groupAuthority;
}
