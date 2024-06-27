package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * Represents a human
 */
public abstract class Human {

    @Id
    @GeneratedValue
    private Long id;
    private String givenName;
    private String familyName;
    private String nickName;

    private BirthSex sex;

    private LocalDate birthDate;
    private LocalDate deathDate;

    private Clan clan;
    private String identifier;

    public Human() {
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

    // Sex-related getters and setters


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
}