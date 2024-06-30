package com.fran.spring_boot_neo4j.controllers;

import com.fran.spring_boot_neo4j.models.Clan;
import com.fran.spring_boot_neo4j.objects.ClanDTO;
import com.fran.spring_boot_neo4j.services.ClanService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


/**
 * Controller class for managing {@link Clan} entities.
 */
@Controller
@RequestMapping("/api/v1/clan")
public class ClanController {

    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    /**
     * Creates a new clan.
     *
     * @param clan the clan to create
     * @return a {@code ResponseEntity} containing the created clan
     */
    @PostMapping("/")
    public ResponseEntity<Clan> createClan(@RequestBody Clan clan) {
        boolean clanExists = clan.getClanName().values().stream().anyMatch(
            name -> clanService.clanNameExists(name)
        );

        if (clanExists) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Generate and set a random identifier
        clan.setIdentifier(UUID.randomUUID().toString());

        // Save the new clan
        Clan savedClan = clanService.saveClan(clan);
        return new ResponseEntity<>(savedClan, HttpStatus.CREATED);
    }

    /**
     * Retrieves a clan by its identifier.
     *
     * @param identifier the identifier of the clan to retrieve
     * @return a {@code ResponseEntity} containing the clan
     */
    @GetMapping("/{identifier}")
    public ResponseEntity<Clan> getClanByIdentifier(@PathVariable String identifier) {
        try {
            Clan clan = clanService.getClanById(identifier);
            return new ResponseEntity<>(clan, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a clan by its name.
     *
     * @param clanName the name of the clan to retrieve
     * @return a {@code ResponseEntity} containing the clan
     */
    @GetMapping("/name/{clanName}")
    public ResponseEntity<Clan> getClanByClanName(@PathVariable String clanName) {
        try {
            Clan clan = clanService.getClanByName(clanName);
            return new ResponseEntity<>(clan, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing clan.
     *
     * @param identifier the identifier of the clan to update
     * @param clan       the updated clan data
     * @return a {@code ResponseEntity} containing the updated clan
     */
    @PutMapping("/{identifier}")
    public ResponseEntity<Clan> updateClan(@PathVariable String identifier,
        @RequestBody Clan clan) {
        try {
            Clan existingClan = clanService.getClanById(identifier);
            existingClan.setClanName(clan.getClanName());
            Clan savedClan = clanService.saveClan(existingClan);
            return new ResponseEntity<>(savedClan, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a clan by its identifier.
     *
     * @param identifier the identifier of the clan to delete
     * @return a {@code ResponseEntity} with a status of OK if deleted, NOT_FOUND otherwise
     */
    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> deleteClan(@PathVariable String identifier) {
        try {
            clanService.deleteClan(identifier);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a sub-clan relationship between two clans.
     *
     * @param parentClanIdentifier the identifier of the parent clan
     * @param subClanIdentifier    the identifier of the sub-clan
     * @return a {@code ResponseEntity} with a status of OK if the relationship was added
     */
    @PostMapping("/relationship/{parentClanIdentifier}/{subClanIdentifier}")
    public ResponseEntity<String> addSubClanRelationship(
        @PathVariable String parentClanIdentifier,
        @PathVariable String subClanIdentifier) {
        clanService.addSubClanRelationshipByIdentifier(parentClanIdentifier, subClanIdentifier);
        return new ResponseEntity<>("Sub-clan relationship successfully added", HttpStatus.OK);
    }

}
