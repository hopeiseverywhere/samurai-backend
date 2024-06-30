package com.fran.spring_boot_neo4j.repositories;

import com.fran.spring_boot_neo4j.models.Clan;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Clan} entities.
 */
@Repository
public interface ClanRepository extends Neo4jRepository<Clan, Long> {

    Optional<Clan> findByClanNameEN(String nameEN);

    Optional<Clan> findByIdentifier(String identifier);

    /**
     * Creates a sub-clan relationship between two clans.
     *
     * @param parentClanIdentifier the identifier of the parent clan
     * @param subClanIdentifier    the identifier of the sub-clan
     */
    @Query("MATCH (parent:Clan {identifier: $parentClanIdentifier}), (sub:Clan {identifier: $subClanIdentifier}) " +
           "MERGE (parent)-[:HSA_SUB_CLAN]->(sub)")
    void addSubClanRelationship(String parentClanIdentifier, String subClanIdentifier);
}
