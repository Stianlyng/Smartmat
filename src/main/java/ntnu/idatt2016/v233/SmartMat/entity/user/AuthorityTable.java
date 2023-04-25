package ntnu.idatt2016.v233.SmartMat.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthorityTable {
    public AuthorityTable(Authority authority) {
        this.authority = authority;
    }

    @Id
    private Authority authority;


    @ManyToMany(mappedBy = "authorities")
    @JsonIgnoreProperties({"authorities", "password"})
    private List<User> users;


    /**
     * Adds a user to the authority
     * @param user user to add
     */
    public void addUser(User user){
        if(users == null)
            users = new ArrayList<>();
        users.add(user);
    }
}
