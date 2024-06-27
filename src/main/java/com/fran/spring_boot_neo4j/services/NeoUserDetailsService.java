package com.fran.spring_boot_neo4j.services;

import com.fran.spring_boot_neo4j.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for loading user-specific data for Spring Security.
 */
@Service
public class NeoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@code NeoUserDetailsService} with the specified {@code UserRepository}.
     *
     * @param userRepository the repository to manage users
     */
    public NeoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user by username.
     *
     * @param username the username of the user to load
     * @return the {@link UserDetails} of the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
