package com.fran.spring_boot_neo4j.objects;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * Data Transfer Object for {@link Samurai}.
 */
@Data
public class SamuraiDTO {

    private Map<String, String> givenName;
    private Map<String, String> familyName;
    private Map<String, String> nickName;
    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String identifier;
    private String relationshipTypeWithParent;
    private List<SamuraiDTO> offspring = new ArrayList<>();
    private boolean isFamilyHead;

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given Name and family Name.
     *
     * @param givenName  the given Name of the samurai
     * @param familyName the family Name of the samurai
     */
    public SamuraiDTO(Map<String, String> givenName, Map<String, String> familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given Name, family Name, and death
     * date.
     *
     * @param givenName  the given Name of the samurai
     * @param familyName the family Name of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(Map<String, String> givenName, Map<String, String> familyName,
        LocalDate deathDate) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.deathDate = deathDate;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given Name, family Name, birth date,
     * and death date.
     *
     * @param givenName  the given Name of the samurai
     * @param familyName the family Name of the samurai
     * @param birthDate  the birthdate of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(Map<String, String> givenName, Map<String, String> familyName,
        LocalDate birthDate, LocalDate deathDate) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.nickName = generateNickName(givenName, familyName);
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given Name, and family
     * Name.
     *
     * @param identifier the identifier of the samurai
     * @param givenName  the given Name of the samurai
     * @param familyName the family Name of the samurai
     */
    public SamuraiDTO(String identifier, Map<String, String> givenName,
        Map<String, String> familyName) {
        this.identifier = identifier;
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = generateNickName(givenName, familyName);
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given Name, family Name,
     * and nickName.
     *
     * @param identifier the identifier of the samurai
     * @param givenName  the given Name of the samurai
     * @param familyName the family Name of the samurai
     * @param nickName   the nickName of the samurai
     */
    public SamuraiDTO(String identifier, Map<String, String> givenName,
        Map<String, String> familyName, Map<String, String> nickName) {
        this.identifier = identifier;
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = nickName;
    }

    /**
     * Generates a nickname by combining the given name and family name.
     *
     * @param givenName  the given name map
     * @param familyName the family name map
     * @return a map of nicknames in different languages
     */
    private Map<String, String> generateNickName(Map<String, String> givenName,
        Map<String, String> familyName) {
        // Implement your logic to generate nicknames in multiple languages
        // This example just concatenates the values with a space
        return Map.of(
            "en", givenName.get("en") + " " + familyName.get("en"),
            "jp", familyName.get("jp") + " " + givenName.get("jp")
        );
    }

    public void addOffspring(SamuraiDTO offspring) {
        this.offspring.add(offspring);
    }

    // Family related
    public boolean isFamilyHead() {
        return isFamilyHead;
    }
}