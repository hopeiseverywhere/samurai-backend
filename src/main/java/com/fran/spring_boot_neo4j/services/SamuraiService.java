package com.fran.spring_boot_neo4j.services;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.models.enums.ParentChildRelationshipType;
import com.fran.spring_boot_neo4j.models.enums.SocialStatus;
import com.fran.spring_boot_neo4j.repositories.SamuraiRepository;
import com.fran.spring_boot_neo4j.requests.AddRelationshipRequest;
import com.fran.spring_boot_neo4j.requests.CreateSamuraiRequest;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing {@link Samurai} entities.
 */
@Service
public class SamuraiService {

    private static final Logger logger = LoggerFactory.getLogger(SamuraiService.class);
    private final SamuraiRepository samuraiRepository;

    /**
     * Constructs a new {@code SamuraiService} with the specified repository.
     *
     * @param samuraiRepository the repository for accessing samurai data
     */
    public SamuraiService(SamuraiRepository samuraiRepository) {
        this.samuraiRepository = samuraiRepository;
    }

    /**
     * Retrieves a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai
     * @return the samurai with the specified identifier
     * @throws ResponseStatusException if the samurai is not found
     */
    public Samurai getSamuraiByIdentifier(String identifier) {
        return samuraiRepository.findSamuraiByIdentifier(identifier)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Samurai not found"));
    }

    /**
     * Creates a new samurai.
     *
     * @param request the request object containing the details of the samurai to create
     * @return the created samurai
     * @throws ResponseStatusException if a samurai with the same given name and family name already
     *                                 exists
     */
    public Samurai createSamurai(CreateSamuraiRequest request) {
        Optional<Samurai> existingSamurai = samuraiRepository
            .findSamuraiByGivenNameAndFamilyName(
                request.getGivenName(), request.getFamilyName());

        if (existingSamurai.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Samurai already exists");
        }

        Samurai samurai = new Samurai();
        samurai.setSocialStatus(SocialStatus.SAMURAI);
        samurai.setGivenName(request.getGivenName());
        samurai.setFamilyName(request.getFamilyName());

        if (request.getNickName() == null || request.getNickName().isEmpty()) {
            samurai.setNickName(request.getFamilyName() + " " + request.getGivenName());
        } else {
            samurai.setNickName(request.getNickName());
        }

        if (request.getBirthDate() != null) {
            samurai.setBirthDate(request.getBirthDate());
        }
        if (request.getDeathDate() != null) {
            samurai.setDeathDate(request.getDeathDate());
        }

        samurai.setIdentifier(UUID.randomUUID().toString());

        Samurai savedSamurai = samuraiRepository.save(samurai);

        if (request.getParentIdentifier() != null && !request.getParentIdentifier().isEmpty()) {
            Samurai parent = getSamuraiByIdentifier(request.getParentIdentifier());
            String type = determineRelationshipType(request.getRelationshipType());
            samuraiRepository.createParentChildRelationship(parent.getIdentifier(),
                savedSamurai.getIdentifier(), type);
        }
        return savedSamurai;
    }

    /**
     * Deletes a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai to delete
     * @throws ResponseStatusException if the samurai is not found
     */
    public void deleteSamurai(String identifier) {
        Samurai samurai = getSamuraiByIdentifier(identifier);
        samuraiRepository.delete(samurai);
    }

    /**
     * Adds a parent-child relationship between two samurai.
     *
     * @param parentIdentifier the identifier of the parent samurai
     * @param childIdentifier  the identifier of the child samurai
     * @param type             the type of relationship (e.g., biological, adopted)
     */
    public void addParentChildRelationship(String parentIdentifier, String childIdentifier,
        String type) {
        samuraiRepository.createParentChildRelationship(parentIdentifier, childIdentifier, type);
    }

     /**
     * Adds a default biological parent-child relationship between two samurai.
     *
     * @param parentIdentifier the identifier of the parent samurai
     * @param childIdentifier  the identifier of the child samurai
     */
    public void addParentChildRelationship(String parentIdentifier, String childIdentifier) {
        samuraiRepository.createParentChildRelationship(parentIdentifier, childIdentifier,
            String.valueOf(ParentChildRelationshipType.BIOLOGICAL));
    }

    /**
     * Adds a parent-child relationship between two samurai.
     *
     * @param request the request object containing the details of the relationship to add
     * @throws ResponseStatusException if the parent or child samurai is not found
     */
    public void addParentChildRelationship(AddRelationshipRequest request) {
        Samurai parent = getSamuraiByIdentifier(request.getParentIdentifier());
        Samurai child = getSamuraiByIdentifier(request.getChildIdentifier());
        String type = determineRelationshipType(request.getRelationshipType());
        samuraiRepository.createParentChildRelationship(parent.getIdentifier(),
            child.getIdentifier(), type);
    }

    /**
     * Determines the relationship type.
     *
     * @param relationshipType the relationship type specified in the request
     * @return the determined relationship type (biological by default if not specified)
     */
    private String determineRelationshipType(String relationshipType) {
        if (relationshipType == null || relationshipType.isEmpty()) {
            return ParentChildRelationshipType.BIOLOGICAL.toString();
        } else {
            return ParentChildRelationshipType.ADOPTED.toString();
        }
    }
}