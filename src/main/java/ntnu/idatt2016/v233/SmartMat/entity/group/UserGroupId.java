package ntnu.idatt2016.v233.SmartMat.entity.group;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a user group id
 *
 * @author Birk
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@Builder
public class UserGroupId implements Serializable {
    @Column(name = "username")
    private String username;
    @Column(name = "group_id")
    private long groupId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroupId)) return false;
        UserGroupId that = (UserGroupId) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getGroupId(), that.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getGroupId());
    }

}
