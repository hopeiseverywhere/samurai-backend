package com.fran.spring_boot_neo4j.models;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user in the system and provides user details for Spring Security.
 */
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private String password;
    private String roles;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoles() {
        return roles;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of {@link GrantedAuthority} representing the roles of the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // e.g. ROLE_USER,ROLE_ADMIN...
        // Split the roles string by commas to get an array of individual roles
        return Arrays.stream(roles.split(","))
            // Map each role string to a SimpleGrantedAuthority object
            .map(SimpleGrantedAuthority::new)
            // Collect the mapped SimpleGrantedAuthority objects into a list
            .toList();
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return {@code true} if the user's account is valid (i.e., non-expired), {@code false} if no
     * longer valid (i.e., expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return {@code true} if the user is not locked, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return {@code true} if the user's credentials are valid (i.e., non-expired), {@code false}
     * if no longer valid (i.e., expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
