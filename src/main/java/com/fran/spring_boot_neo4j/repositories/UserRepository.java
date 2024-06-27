package com.fran.spring_boot_neo4j.repositories;

import com.fran.spring_boot_neo4j.models.User;
import com.fran.spring_boot_neo4j.queryresults.CourseEnrollmentQueryResult;
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

    /**
     * Finds the enrollment status of a user for a specific course.
     *
     * @param username   the username of the user
     * @param identifier the identifier of the course
     * @return {@code true} if the user is enrolled in the course, {@code false} otherwise
     */
    @Query(
        "MATCH (user:User), (course:Course) WHERE user.username = $username " +
            "AND course.identifier = $identifier " +
            "RETURN EXISTS((user)-[:ENROLLED_IN]->(course))")
    Boolean findEnrollmentStatus(String username, String identifier);

    /**
     * Creates an enrollment relationship between a user and a course.
     *
     * @param username   the username of the user
     * @param identifier the identifier of the course
     * @return a {@link CourseEnrollmentQueryResult} containing the user and course involved in the
     * enrollment relationship
     */
    @Query(
        "MATCH (user:User), (course:Course) WHERE user.username = $username "
            + "AND course.identifier = $identifier " +
            "CREATE (user)-[:ENROLLED_IN]->(course) RETURN user, course")
    CourseEnrollmentQueryResult createEnrollmentRelationship(String username, String identifier);
}