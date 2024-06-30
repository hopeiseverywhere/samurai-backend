package com.fran.spring_boot_neo4j.requests;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;

/**
 * Request object for creating a new samurai.
 */
public class CreateSamuraiRequest {

    private String givenName;
    private String familyName;
    private String nickName;
    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String parentIdentifier;
    private String relationshipType;

    private boolean isFamilyHead;

    private String clanName;

    /**
     * 氏
     */
    private String uji;

    /**
     * 八色の姓
     */
    private String kabane;

    /**
     * Default constructor.
     */
    public CreateSamuraiRequest() {
    }

    /**
     * Constructs a new {@code CreateSamuraiRequest} with the specified given name and family name.
     * The nickname is automatically generated as "familyName givenName".
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     */
    public CreateSamuraiRequest(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = familyName + " " + givenName;
    }

    /**
     * Constructs a new {@code CreateSamuraiRequest} with the specified given name, family name, and
     * nickname.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @param nickName   the nickname of the samurai
     */
    public CreateSamuraiRequest(String givenName, String familyName, String nickName) {
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

    // Parent related

    public String getParentIdentifier() {
        return parentIdentifier;
    }

    public void setParentIdentifier(String parentIdentifier) {
        this.parentIdentifier = parentIdentifier;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    // Family related

    public boolean isFamilyHead() {
        return isFamilyHead;
    }

    public void setFamilyHead(boolean familyHead) {
        isFamilyHead = familyHead;
    }

    // Shisei related

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

    // Clan related

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }
}