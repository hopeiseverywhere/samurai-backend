package com.fran.spring_boot_neo4j.controllers;

import com.fran.spring_boot_neo4j.models.Clan;
import com.fran.spring_boot_neo4j.repositories.ClanRepository;
import java.util.Optional;
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


/**
 * Controller class for managing {@link Clan} entities.
 */
@Controller
@RequestMapping("/api/v1/clan")
public class ClanController {

    private final ClanRepository clanRepository;

    public ClanController(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    /**
     * Creates a new clan.
     *
     * @param clan the clan to create
     * @return a {@code ResponseEntity} containing the created clan
     */
    @PostMapping("/")
    public ResponseEntity<Clan> createClan(@RequestBody Clan clan) {
        Optional<Clan> existingClan = clanRepository.findByClanNameEN(clan.getClanNameEN());
        if (existingClan.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        clan.setIdentifier(UUID.randomUUID().toString());
        Clan savedClan = clanRepository.save(clan);
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
        Optional<Clan> clan = clanRepository.findByIdentifier(identifier);
        return clan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves a clan by its name.
     *
     * @param clanName the name of the clan to retrieve
     * @return a {@code ResponseEntity} containing the clan
     */
    @GetMapping("/name/{clanName}")
    public ResponseEntity<Clan> getClanByClanName(@PathVariable String clanName) {
        Optional<Clan> clan = clanRepository.findByClanNameEN(clanName);
        return clan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        Optional<Clan> existingClan = clanRepository.findByIdentifier(identifier);
        if (!existingClan.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Clan updatedClan = existingClan.get();
        updatedClan.setClanNameEN(clan.getClanNameEN());
        Clan savedClan = clanRepository.save(updatedClan);
        return new ResponseEntity<>(savedClan, HttpStatus.OK);
    }

    /**
     * Deletes a clan by its identifier.
     *
     * @param identifier the identifier of the clan to delete
     * @return a {@code ResponseEntity} with a status of OK if deleted, NOT_FOUND otherwise
     */
    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> deleteClan(@PathVariable String identifier) {
        Optional<Clan> existingClan = clanRepository.findByIdentifier(identifier);
        if (!existingClan.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clanRepository.delete(existingClan.get());
        return new ResponseEntity<>(HttpStatus.OK);
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
        clanRepository.addSubClanRelationship(parentClanIdentifier, subClanIdentifier);
        return new ResponseEntity<>("Sub-clan relationship successfully added", HttpStatus.OK);
    }

}
