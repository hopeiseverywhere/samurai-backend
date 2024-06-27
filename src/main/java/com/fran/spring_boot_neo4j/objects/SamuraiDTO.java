package com.fran.spring_boot_neo4j.objects;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for samurai.
 */
public class SamuraiDTO {

    private String givenName;
    private String familyName;
    private String nickName;
    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String identifier;
    private String relationshipTypeWithParent;
    private List<SamuraiDTO> offspring = new ArrayList<>();

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given name and family name.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     */
    public SamuraiDTO(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given name, family name, and death date.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(String givenName, String familyName, LocalDate deathDate) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.deathDate = deathDate;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given name, family name, birth date, and death date.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @param birthDate  the birth date of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(String givenName, String familyName, LocalDate birthDate, LocalDate deathDate) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.nickName = givenName + " " + familyName;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given name, and family name.
     *
     * @param identifier the identifier of the samurai
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     */
    public SamuraiDTO(String identifier, String givenName, String familyName) {
        this.identifier = identifier;
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = givenName + " " + familyName;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given name, family name, and nickname.
     *
     * @param identifier the identifier of the samurai
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @param nickName   the nickname of the samurai
     */
    public SamuraiDTO(String identifier, String givenName, String familyName, String nickName) {
        this.identifier = identifier;
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = nickName;
    }

    // Name-related getters and setters


    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }


    public String getFamilyName() {
        return familyName;
    }


    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    // Birth sex related

    public BirthSex getSex() {
        return sex;
    }


    public void setSex(BirthSex sex) {
        this.sex = sex;
    }

    // Birth and death date getters and setters
    public LocalDate getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public LocalDate getDeathDate() {
        return deathDate;
    }


    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    // Identifier getters and setters


    public String getIdentifier() {
        return identifier;
    }


    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    // Relationship type getters and setters


    public String getRelationshipTypeWithParent() {
        return relationshipTypeWithParent;
    }


    public void setRelationshipTypeWithParent(String relationshipType) {
        this.relationshipTypeWithParent = relationshipType;
    }

    // Offspring-related getters and setters


    public List<SamuraiDTO> getOffspring() {
        return offspring;
    }


    public void setOffspring(List<SamuraiDTO> offspring) {
        this.offspring = offspring;
    }


    public void addOffspring(SamuraiDTO offspring) {
        this.offspring.add(offspring);
    }
}