package com.fran.spring_boot_neo4j.services;

import com.fran.spring_boot_neo4j.models.User;
import com.fran.spring_boot_neo4j.repositories.UserRepository;
import com.fran.spring_boot_neo4j.requests.CreateUserRequest;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing {@link User} entities.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@code UserService} with the specified {@code UserRepository} and
     * {@code PasswordEncoder}.
     *
     * @param userRepository  the repository to manage users
     * @param passwordEncoder the password encoder to encode user passwords
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user based on the provided {@code CreateUserRequest}.
     *
     * @param request the request object containing user details
     * @return the created {@link User} entity
     */
    public User createUser(CreateUserRequest request) {
        // Check if the username already exists
        Optional<User> existingUser = userRepository.findUserByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // Create a new user entity
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setRoles(request.getRoles());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save the user to the repository
        userRepository.save(user);
        return user;
    }
}
