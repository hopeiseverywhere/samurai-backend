package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.List;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * Represents a human
 */
public abstract class Human {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private Long id;

    // Names
    private String givenName;
    private String familyName;
    private String nickName;

    // Sex and birthdate
    private BirthSex sex;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @Relationship(type = "BELONGS_TO")
    private Clan clan;
    private String identifier;

    private boolean isFamilyHead;

    // Myoji related
    /**
     * 氏
     */
    private String uji;

    /**
     * 八色の姓
     */
    private String kabane;

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

    // Clan-related
    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    // Family-related
    public boolean isFamilyHead() {
        return isFamilyHead;
    }

    public void setFamilyHead(boolean familyHead) {
        isFamilyHead = familyHead;
    }

    //　Shisei related (氏姓)
    public String getUji() {
        return uji;
    }

    public void setUji(String uji) {
        this.uji = uji;
    }

    public String getKabane() {
        return kabane;
    }

    public void setKabane(String kabane) {
        this.kabane = kabane;
    }

}