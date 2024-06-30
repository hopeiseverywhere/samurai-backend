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
     * Finds a samurai by any of its given names and family names in different languages.
     * This query checks if the provided givenName and familyName exist in the respective
     * properties, which are maps containing names in multiple languages.
     *
     * @param givenName  the given name of the samurai in any language
     * @param familyName the family name of the samurai in any language
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    @Query("MATCH (s:Samurai) WHERE $givenName IN s.givenName AND $familyName IN s.familyName RETURN s")
    Optional<Samurai> findSamuraiByGivenNameAndFamilyName(String givenName, String familyName);

    /**
     * Finds a samurai by any of its nicknames in different languages.
     * This query checks if the provided nickName exists in the nickName property, which is a map
     * containing nicknames in multiple languages.
     *
     * @param nickName the nickname of the samurai in any language
     * @return an {@link Optional} containing the found samurai, or empty if no samurai found
     */
    @Query("MATCH (s:Samurai) WHERE $nickName IN s.nickName RETURN s")
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
            + "MERGE (parent)-[r:PARENT_OF {type: $relationshipType}]->(child) " + "RETURN r")
    void createParentChildRelationship(String parentIdentifier, String childIdentifier,
        String relationshipType);

    /**
     * Finds all offspring of a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai
     * @return a list of offspring samurai with relationship type
     */
    @Query("MATCH (parent:Samurai {identifier: $identifier})-[r:PARENT_OF]->(offspring:Samurai) "
        + "RETURN offspring AS offspring, r.type AS relationshipType")
    List<SamuraiOffspringQueryResult> findAllOffspringByIdentifierWithType(String identifier);

    /**
     * Adds a relationship between a human and a clan. This query matches a human with the specified
     * identifier and a clan with the specified identifier, then creates a relationship indicating
     * that the human belongs to the clan.
     *
     * @param humanIdentifier the identifier of the human
     * @param clanIdentifier  the identifier of the clan
     */
    @Query(
        "MATCH (h:Human {identifier: $humanIdentifier}), (c:Clan {identifier: $clanIdentifier}) " +
            "MERGE (h)-[:BELONGS_TO]->(c)")
    void addClanToHuman(String humanIdentifier, String clanIdentifier);
}