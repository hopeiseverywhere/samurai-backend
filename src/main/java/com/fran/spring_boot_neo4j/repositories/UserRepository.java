package com.fran.spring_boot_neo4j.repositories;

import com.fran.spring_boot_neo4j.models.User;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link User} entities.
 */
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the found user, or empty if no user found
     */
    Optional<User> findUserByUsername(String username);

}