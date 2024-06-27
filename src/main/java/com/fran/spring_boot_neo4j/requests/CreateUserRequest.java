package com.fran.spring_boot_neo4j.requests;

/**
 * Request object for creating a new user.
 */
public class CreateUserRequest {

    private String name;
    private String username;

    private String password;
    private String roles;

    /**
     * Constructs a new {@code CreateUserRequest} with the specified name, username, password, and
     * roles.
     *
     * @param name     the name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param roles    the roles assigned to the user
     */
    public CreateUserRequest(String name, String username, String password, String roles) {
        this.name = name;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
