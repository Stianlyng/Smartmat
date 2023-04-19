package ntnu.idatt2016.v233.SmartMat.entity.user;


import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User is a class representing a user in the system.
 * It implements the UserDetails interface.
 *
 * @author Anders and Birk
 * @version 2.0
 * @since 05.04.2023
 *
 */
@Entity(name = "users")
@Builder

public class User implements UserDetails {
    @Id
    @Column(name = "user_name")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    /**
     * used when created jwts and validating user authority
     * @return the users authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority.name()));
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
}
