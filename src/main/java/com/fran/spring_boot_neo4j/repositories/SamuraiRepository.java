package com.fran.spring_boot_neo4j.repositories;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.queryresults.SamuraiOffspringQueryResult;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Samurai} entities.
 */
@Repository
public interface SamuraiRepository extends Neo4jRepository<Samurai, Long> {

    /**
     * Finds a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    Optional<Samurai> findSamuraiByIdentifier(String identifier);

    /**
     * Finds a samurai by its given name and family name.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    Optional<Samurai> findSamuraiByGivenNameAndFamilyName(String givenName, String familyName);

    /**
     * Finds a samurai by its nickname.
     *
     * @param nickName the nickname of the samurai
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    Optional<Samurai> findSamuraiByNickName(String nickName);

    /**
     * Finds a samurai by its birthdate.
     *
     * @param birthDate the birthdate of the samurai
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    Optional<Samurai> findSamuraiByBirthDate(LocalDate birthDate);

    /**
     * Finds a samurai by its death date.
     *
     * @param deathDate the death date of the samurai
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    Optional<Samurai> findSamuraiByDeathDate(LocalDate deathDate);

    /**
     * Creates a parent-child relationship between two samurai.
     *
     * @param parentIdentifier the identifier of the parent samurai
     * @param childIdentifier  the identifier of the child samurai
     * @param relationshipType the type of the relationship
     */
    @Query(
        "MATCH (parent:Samurai {identifier: $parentIdentifier}), (child:Samurai {identifier: $childIdentifier}) "
            +
            "MERGE (parent)-[r:PARENT_CHILD {type: $relationshipType}]->(child) " +
            "RETURN r")
    void createParentChildRelationship(String parentIdentifier, String childIdentifier,
        String relationshipType);

    /**
     * Finds all offspring of a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai
     * @return a list of offspring samurai with relationship type
     */
    @Query("MATCH (parent:Samurai {identifier: $identifier})-[r:PARENT_CHILD]->(offspring:Samurai) " +
           "RETURN offspring AS offspring, r.type AS relationshipType")
    List<SamuraiOffspringQueryResult> findAllOffspringByIdentifierWithType(String identifier);
}