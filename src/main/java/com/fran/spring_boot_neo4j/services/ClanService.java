package com.fran.spring_boot_neo4j.services;

import com.fran.spring_boot_neo4j.models.Clan;
import com.fran.spring_boot_neo4j.repositories.ClanRepository;
import com.fran.spring_boot_neo4j.repositories.SamuraiRepository;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing {@link Clan} entities.
 */
@Service
public class ClanService {

    private final ClanRepository clanRepository;
    private final SamuraiRepository samuraiRepository;

    /**
     * Constructs a new {@code ClanService} with the specified repositories.
     *
     * @param clanRepository    the repository for accessing clan data
     * @param samuraiRepository the repository for accessing samurai data
     */
    public ClanService(ClanRepository clanRepository, SamuraiRepository samuraiRepository) {
        this.clanRepository = clanRepository;
        this.samuraiRepository = samuraiRepository;
    }

    public Clan saveClan(Clan clan) {
        return clanRepository.save(clan);
    }

    /**
     * Checks if a clan with the specified name exists in any language.
     *
     * @param name the name of the clan
     * @return true if a clan with the specified name exists, false otherwise
     */
    public boolean clanNameExists(String name) {
        return clanRepository.findByClanName(name).isPresent();
    }

    /**
     * Retrieves a clan by its name.
     *
     * @param name the name of the clan
     * @return the clan with the specified name
     * @throws ResponseStatusException if the clan is not found
     */
    public Clan getClanByName(String name) {
        return clanRepository.findByClanName(name)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clan not found"));
    }

    /**
     * Retrieves a clan by its identifier.
     *
     * @param identifier the identifier of the clan
     * @return the clan with the specified identifier
     * @throws ResponseStatusException if the clan is not found
     */
    public Clan getClanById(String identifier) {
        return clanRepository.findByIdentifier(identifier)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clan not found"));
    }

    /**
     * Deletes a clan by its identifier.
     *
     * @param identifier the identifier of the clan to delete
     * @throws ResponseStatusException if the clan is not found
     */
    public void deleteClan(String identifier) {
        Clan clan = clanRepository.findByIdentifier(identifier)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clan not found"));
        clanRepository.delete(clan);
    }

    /**
     * Creates a new clan with the specified name.
     *
     * @param clanName the name of the clan to create
     * @return the created clan
     * @throws ResponseStatusException if a clan with the same name already exists
     */
    public Clan createClan(Map<String, String> clanName) {
        // Check for existence by any of the names in the map
        for (String name : clanName.values()) {
            if (clanRepository.findByClanName(name).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Clan already exists");
            }
        }

        Clan clan = new Clan(clanName);
        return clanRepository.save(clan);
    }

    /**
     * Adds a clan to a human.
     *
     * @param humanIdentifier the identifier of the human
     * @param clanName        the name of the clan
     * @throws ResponseStatusException if the clan is not found
     */
    public void addClanToHuman(String humanIdentifier, Map<String, String> clanName) {
        Clan clan = clanRepository.findByClanName(clanName.values().iterator().next())
            .orElseGet(() -> clanRepository.save(new Clan(clanName)));
        samuraiRepository.addClanToHuman(humanIdentifier, clan.getIdentifier());
    }


    /**
     * Adds a sub-clan relationship between two clans by their names.
     *
     * @param clanName       the name of the sub-clan
     * @param parentClanName the name of the parent clan
     * @throws ResponseStatusException if either clan is not found
     */
    public void addSubClanRelationshipByName(Map<String, String> clanName,
        Map<String, String> parentClanName) {
        Clan clan = clanRepository.findByClanName(clanName.values().iterator().next())
            .orElseGet(() -> clanRepository.save(new Clan(clanName)));
        Clan parentClan = clanRepository.findByClanName(parentClanName.values().iterator().next())
            .orElseGet(() -> clanRepository.save(new Clan(parentClanName)));
        addSubClanRelationshipByIdentifier(parentClan.getIdentifier(), clan.getIdentifier());
    }


    /**
     * Adds a sub-clan relationship between two clans by their identifiers.
     *
     * @param parentClanIdentifier the identifier of the parent clan
     * @param subClanIdentifier    the identifier of the sub-clan
     * @throws ResponseStatusException if either clan is not found
     */
    public void addSubClanRelationshipByIdentifier(String parentClanIdentifier,
        String subClanIdentifier) {
        clanRepository.addSubClanRelationship(parentClanIdentifier, subClanIdentifier);

    }


}
