package ntnu.idatt2016.v233.SmartMat.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "groups")
@Data
public class Groups {

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
