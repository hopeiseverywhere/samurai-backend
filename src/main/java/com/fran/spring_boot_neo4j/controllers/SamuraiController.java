package com.fran.spring_boot_neo4j.controllers;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.objects.SamuraiDTO;
import com.fran.spring_boot_neo4j.requests.AddRelationshipRequest;
import com.fran.spring_boot_neo4j.requests.CreateSamuraiRequest;
import com.fran.spring_boot_neo4j.services.SamuraiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing {@link Samurai} entities.
 */
@Controller
@RequestMapping("/api/v1/samurai")
public class SamuraiController {

    private final SamuraiService samuraiService;
    private static final Logger logger = LoggerFactory.getLogger(SamuraiController.class);

    /**
     * Constructs a new {@code SamuraiController} with the specified samurai service.
     *
     * @param samuraiService the samurai service to use
     */
    public SamuraiController(SamuraiService samuraiService) {
        this.samuraiService = samuraiService;
    }

    /**
     * Retrieves a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai to retrieve
     * @return a {@code ResponseEntity} containing the samurai data
     */
    @GetMapping("/{identifier}")
    public ResponseEntity<SamuraiDTO> getSamuraiByIdentifier(@PathVariable String identifier) {
        Samurai samurai = samuraiService.getSamuraiByIdentifier(identifier);
        SamuraiDTO responseSamurai = new SamuraiDTO(samurai.getGivenNameEN(), samurai.getFamilyNameEN(),
            samurai.getBirthDate(), samurai.getDeathDate());

        return new ResponseEntity<>(responseSamurai, HttpStatus.OK);
    }

    /**
     * Creates a new samurai.
     *
     * @param request the request object containing the details of the samurai to create
     * @return a {@code ResponseEntity} containing the created samurai
     */
    @PostMapping("/")
    public ResponseEntity<Samurai> createSamurai(@RequestBody CreateSamuraiRequest request) {
        Samurai createdSamurai = samuraiService.createSamurai(request);
        return new ResponseEntity<>(createdSamurai, HttpStatus.CREATED);
    }

    /**
     * Deletes a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai to delete
     * @return a {@code ResponseEntity} containing a success message
     */
    @DeleteMapping("/{identifier}")
    public ResponseEntity<String> deleteSamurai(@PathVariable String identifier) {
        samuraiService.deleteSamurai(identifier);
        return new ResponseEntity<>("Samurai successfully deleted", HttpStatus.OK);
    }

    /**
     * Adds a parent-child relationship between two samurai.
     *
     * @param request the request object containing the details of the relationship to add
     * @return a {@code ResponseEntity} containing a success message
     */
    @PostMapping("/relationship")
    public ResponseEntity<String> addParentChildRelationship(
        @RequestBody AddRelationshipRequest request) {
        samuraiService.addParentChildRelationship(request.getParentIdentifier(),
            request.getChildIdentifier(), request.getRelationshipType());
        return new ResponseEntity<>("Relationship successfully added", HttpStatus.OK);
    }

    /**
     * Adds a parent-child relationship between two samurai.
     *
     * @param parentIdentifier the identifier of the parent samurai
     * @param childIdentifier  the identifier of the child samurai
     * @param rType            the type of the relationship (default is "BIOLOGICAL")
     * @return a {@code ResponseEntity} containing a success message
     */
    @PostMapping("/relationship/{parentIdentifier}/{childIdentifier}")
    public ResponseEntity<String> addParentChildRelationship(@PathVariable String parentIdentifier,
        @PathVariable String childIdentifier,
        @RequestParam(value = "rType", defaultValue = "BIOLOGICAL") String rType) {

        Samurai parent = samuraiService.getSamuraiByIdentifier(parentIdentifier);
        Samurai child = samuraiService.getSamuraiByIdentifier(childIdentifier);

        samuraiService.addParentChildRelationship(parentIdentifier, childIdentifier, rType);

        return new ResponseEntity<>("Relationship successfully added", HttpStatus.OK);
    }

    /**
     * Retrieves all offspring of a samurai in a tree structure.
     *
     * @param identifier the identifier of the samurai
     * @return a {@code ResponseEntity} containing the tree structure of offspring
     */
    @GetMapping("/offspring/{identifier}")
    public ResponseEntity<SamuraiDTO> getSamuraiOffspring(@PathVariable String identifier) {
        SamuraiDTO samuraiTree = samuraiService.getSamuraiTree(identifier);
        return new ResponseEntity<>(samuraiTree, HttpStatus.OK);
    }

    /**
     * Adds an existing samurai to a clan.
     *
     * @param samuraiIdentifier the identifier of the samurai
     * @param clanName          the name of the clan
     * @return a {@code ResponseEntity} containing a success message
     */
    @PostMapping("/{samuraiIdentifier}/{clanName}")
    public ResponseEntity<String> addSamuraiToClan(@PathVariable String samuraiIdentifier,
        @PathVariable String clanName) {
        samuraiService.addSamuraiToClan(samuraiIdentifier, clanName);
        return new ResponseEntity<>("Samurai added to clan successfully", HttpStatus.OK);
    }
}