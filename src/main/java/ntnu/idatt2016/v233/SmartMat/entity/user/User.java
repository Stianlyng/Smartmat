package ntnu.idatt2016.v233.SmartMat.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ntnu.idatt2016.v233.SmartMat.dto.enums.Authority;
import ntnu.idatt2016.v233.SmartMat.entity.Recipe;
import ntnu.idatt2016.v233.SmartMat.entity.group.UserGroupAsso;
import ntnu.idatt2016.v233.SmartMat.entity.product.Allergy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.*;

/**
 * User is a class representing a user in the system.
 * It implements the UserDetails interface.
 *
 * @author Anders and Birk
 * @version 2.0.3
 * @since 25.04.2023
 *
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    private Date dateOfBirth;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<UserGroupAsso> group;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "users")
    @JsonIgnoreProperties({"users", "products"})
    private List<Allergy> allergies;


    @ManyToMany(mappedBy = "users",
            fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties({"users"})
    private Set<Recipe> recipes;


    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Authority authority;



    /**
     * adds an allergy to the user
     * @param allergy the allergy to add to the user
     */
    public void addAllergy(Allergy allergy){
        if (this.allergies == null) {
            this.allergies = new ArrayList<>();
        }
        this.allergies.add(allergy);
    }


    /**
     * Deletes the specified allergy from this user's list of allergies.
     *
     * @param allergy the allergy to delete
     * @return true if the allergy was successfully deleted, false otherwise
     */
    public boolean deleteAllergy(Allergy allergy){
        if(this.allergies == null){
            this.allergies = new ArrayList<>();
        }
        if(this.allergies.contains(allergy)){
            allergies.remove(allergy);
            return true;
        }
        return false;
    }

    /**
     * adds a recipe to the user
     * @param recipe the recipe to add to the user
     */
    public void addRecipe(Recipe recipe){
        if (this.recipes == null) {
            this.recipes = new HashSet<>();
        }
        this.recipes.add(recipe);
    }


    /**
     * adds a group to the user
     * @param userGroupTable the userGroupTable to add to the user
     */
    public void addGroup(UserGroupAsso userGroupTable){
        if (this.group == null) {
            this.group = new ArrayList<>();
        }
        this.group.add(userGroupTable);
    }

    /**
     * used when created jwts and validating user authority
     * @return the users authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.authority.toString()));
    }

    /**
     * getter for the user password used in userdetails service
     * @return the users password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * getter for the user username used in userdetails service
     * @return the users username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * not used
     * @return true if the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * not used
     * @return true if the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * not used
     * @return true if the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * used in authentication
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o){

        if(o instanceof User user){
            return user.getUsername().equals(this.username);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(username);
    }


}
