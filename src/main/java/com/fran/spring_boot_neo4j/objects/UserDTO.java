package com.fran.spring_boot_neo4j.objects;
import com.fran.spring_boot_neo4j.models.User;

/**
 * Data Transfer Object (DTO) for transferring user {@link User} data.
 */
public class UserDTO {

    // We don't want DTO to include user's password
    private String name;
    private String username;
    private String roles;

    /**
     * Constructs a new {@code UserDTO} with the specified name, username, and roles.
     *
     * @param name     the name of the user
     * @param username the username of the user
     * @param roles    the roles assigned to the user
     */
    public UserDTO(String name, String username, String roles) {
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
