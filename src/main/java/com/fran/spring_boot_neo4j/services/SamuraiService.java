package com.fran.spring_boot_neo4j.services;

import com.fran.spring_boot_neo4j.models.Clan;
import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.models.enums.ParentChildRelationshipType;
import com.fran.spring_boot_neo4j.models.enums.SocialStatus;
import com.fran.spring_boot_neo4j.objects.SamuraiDTO;
import com.fran.spring_boot_neo4j.queryresults.SamuraiOffspringQueryResult;
import com.fran.spring_boot_neo4j.repositories.ClanRepository;
import com.fran.spring_boot_neo4j.repositories.SamuraiRepository;
import com.fran.spring_boot_neo4j.requests.AddRelationshipRequest;
import com.fran.spring_boot_neo4j.requests.CreateSamuraiRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final ClanRepository clanRepository;

    /**
     * Constructs a new {@code SamuraiService} with the specified repository.
     *
     * @param samuraiRepository the repository for accessing samurai data
     * @param clanRepository    the repository for accessing clan data
     */
    public SamuraiService(SamuraiRepository samuraiRepository, ClanRepository clanRepository) {
        this.samuraiRepository = samuraiRepository;
        this.clanRepository = clanRepository;
    }

    /**
     * Retrieves a samurai by its identifier.
     *
     * @param identifier the identifier of the samurai
     * @return the samurai with the specified identifier
     * @throws ResponseStatusException if the samurai is not found
     */
    public Samurai getSamuraiByIdentifier(String identifier) {
        return samuraiRepository.findSamuraiByIdentifier(identifier).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Samurai not found"));
    }


    /**
     * Searches for samurai by nickname.
     *
     * @param nickName the nickname to search for
     * @return a list of samurai DTOs matching the nickname
     */
    public List<SamuraiDTO> searchSamuraiByNickName(String nickName) {
        List<Samurai> samurais = samuraiRepository.searchAllSamuraisByNickName(nickName);
        return samurais.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
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
        // Check for uniqueness by iterating through the available keys in the givenName and familyName maps
        boolean samuraiExists = request.getGivenName().entrySet().stream().anyMatch(
            entry -> samuraiRepository.findSamuraiByGivenNameAndFamilyName(entry.getValue(),
                request.getFamilyName().get(entry.getKey())).isPresent()
        );

        if (samuraiExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Samurai already exists");
        }

        // Create and save new samurai
        Samurai samurai = new Samurai();
        samurai.setSocialStatus(SocialStatus.SAMURAI);
        samurai.setGivenName(request.getGivenName());
        samurai.setFamilyName(request.getFamilyName());

        // Generate nickname if not provided
        if (request.getNickName() == null || request.getNickName().isEmpty()) {
            Map<String, String> nickName = request.generateNickName(request.getFamilyName(),
                request.getGivenName());
            samurai.setNickName(nickName);
        } else {
            samurai.setNickName(request.getNickName());
        }

        // Set birthDate and deathDate if provided
        if (request.getBirthDate() != null) {
            samurai.setBirthDate(request.getBirthDate());
        }
        if (request.getDeathDate() != null) {
            samurai.setDeathDate(request.getDeathDate());
        }

        // Set family head status
        samurai.setFamilyHead(request.isFamilyHead());

        // Set Shisei related if provided
        if (request.getUji() != null) {
            samurai.setUji(request.getUji());
        }
        if (request.getKabane() != null) {
            samurai.setKabane(request.getKabane());
        }

        // Generate and set a random identifier
        //samurai.setIdentifier(UUID.randomUUID().toString());

        // Set clan if provided
        Clan clan = getOrCreateClan(request);
        samurai.setClan(clan);

        Samurai savedSamurai = samuraiRepository.save(samurai);

        // Generate parent-child relationship if parent identifier is provided
        // Generate parent-child relationship if parent identifier is provided
        if (request.getParentIdentifier() != null && !request.getParentIdentifier().isEmpty()) {
            Samurai parent = getSamuraiByIdentifier(request.getParentIdentifier());
            String type = determineRelationshipType(request.getRelationshipType());
            samuraiRepository.createParentChildRelationship(parent.getIdentifier(),
                savedSamurai.getIdentifier(), type);
        }

        return savedSamurai;
    }

    /**
     * Helper method to get or create a clan based on the provided request.
     *
     * @param request the request object containing the clan details
     * @return the existing or newly created clan
     */
    private Clan getOrCreateClan(CreateSamuraiRequest request) {
        Map<String, String> clanName;
        if (request.getClanName() != null && !request.getClanName().isEmpty()) {
            clanName = request.getClanName();
        } else {
            clanName = new HashMap<>(request.getFamilyName());
        }

        Optional<Clan> existingClanOpt = clanName.values().stream()
            .map(clanRepository::findByClanName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();

        if (existingClanOpt.isPresent()) {
            Clan existingClan = existingClanOpt.get();
            existingClan.setClanName(clanName);
            return clanRepository.save(existingClan);
        } else {
            Clan newClan = new Clan();
            newClan.setClanName(clanName);
            return clanRepository.save(newClan);
        }
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

    /**
     * Retrieves the entire tree of samurai starting from the given identifier.
     *
     * @param identifier the identifier of the root samurai
     * @return the DTO representation of the samurai tree
     */
    public SamuraiDTO getSamuraiTree(String identifier) {
        Samurai rootSamurai = getSamuraiByIdentifier(identifier);
        SamuraiDTO rootDTO = convertToDTO(rootSamurai);

        // Map to keep track of visited samurai
        Map<String, SamuraiDTO> visited = new HashMap<>();
        visited.put(rootDTO.getIdentifier(), rootDTO);

        buildTree(rootDTO, visited);

        return rootDTO;
    }

    /**
     * Builds the samurai tree recursively.
     *
     * @param parentDTO the parent samurai DTO
     * @param visited   the map of visited samurai
     */
    private void buildTree(SamuraiDTO parentDTO, Map<String, SamuraiDTO> visited) {
        List<SamuraiOffspringQueryResult> children = samuraiRepository.findAllOffspringByIdentifierWithType(
            parentDTO.getIdentifier());

        for (SamuraiOffspringQueryResult childData : children) {
            Samurai child = childData.getOffspring();
            String relationshipType = childData.getRelationshipType();

            // Avoid cyclic relationships by checking if the samurai has been visited
            if (!visited.containsKey(child.getIdentifier())) {
                SamuraiDTO childDTO = convertToDTO(child);
                childDTO.setRelationshipTypeWithParent(relationshipType);
                visited.put(child.getIdentifier(), childDTO);
                parentDTO.addOffspring(childDTO);
                buildTree(childDTO, visited);
            }
        }
    }

    /**
     * Converts a samurai entity to its DTO representation.
     *
     * @param samurai the samurai entity
     * @return the DTO representation of the samurai
     */
    private SamuraiDTO convertToDTO(Samurai samurai) {
        SamuraiDTO dto = new SamuraiDTO(
            samurai.getIdentifier(),
            samurai.getGivenName(),
            samurai.getFamilyName()
        );
        dto.setNickName(samurai.getNickName());
        dto.setBirthDate(samurai.getBirthDate());
        dto.setDeathDate(samurai.getDeathDate());
        dto.setSex(samurai.getSex());
        dto.setFamilyHead(samurai.isFamilyHead());
        return dto;
    }

    /**
     * Adds an existing samurai to a clan.
     *
     * @param samuraiIdentifier the identifier of the samurai
     * @param clanName          the name of the clan
     * @throws ResponseStatusException if the samurai or clan is not found
     */
    public void addSamuraiToClan(String samuraiIdentifier, String clanName) {
        Samurai samurai = getSamuraiByIdentifier(samuraiIdentifier);

        // Determine the appropriate language for the clan name from the samurai's family name map
        String appropriateLanguage = null;
        for (Map.Entry<String, String> entry : samurai.getFamilyName().entrySet()) {
            if (entry.getValue().equals(clanName)) {
                appropriateLanguage = entry.getKey();
                break;
            }
        }

        // If no appropriate language is found, use the first language available in the family name map
        if (appropriateLanguage == null && !samurai.getFamilyName().isEmpty()) {
            appropriateLanguage = samurai.getFamilyName().keySet().iterator().next();
        }

        // Retrieve or create the clan
        String finalAppropriateLanguage = appropriateLanguage;
        Clan clan = clanRepository.findByClanName(clanName)
            .orElseGet(() -> {
                Clan newClan = new Clan();
                Map<String, String> clanNames = new HashMap<>();
                if (finalAppropriateLanguage != null) {
                    clanNames.put(finalAppropriateLanguage, clanName);
                }
                newClan.setClanName(clanNames);
                return clanRepository.save(newClan);
            });

        // Set the samurai's clan and save the samurai
        samurai.setClan(clan);
        samuraiRepository.save(samurai);
    }

}