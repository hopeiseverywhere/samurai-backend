package com.fran.spring_boot_neo4j.requests;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.Map;
import lombok.Data;

/**
 * Request object for creating a new samurai.
 */
@Data
public class CreateSamuraiRequest {

    private Map<String, String> givenName;
    private Map<String, String> familyName;
    private Map<String, String> nickName;
    private Map<String, String> clanName;
    /**
     * 氏
     */
    private Map<String, String> uji;

    /**
     * 八色の姓
     */

    private Map<String, String> kabane;

    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String parentIdentifier;
    private String relationshipType;

    private boolean isFamilyHead;


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
    public CreateSamuraiRequest(Map<String, String> givenName, Map<String, String> familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = generateNickName(familyName, givenName);
    }

    /**
     * Constructs a new {@code CreateSamuraiRequest} with the specified given name, family name, and
     * nickname.
     *
     * @param givenName  the given name of the samurai
     * @param familyName the family name of the samurai
     * @param nickName   the nickname of the samurai
     */
    public CreateSamuraiRequest(Map<String, String> givenName, Map<String, String> familyName,
        Map<String, String> nickName) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.nickName = nickName;
    }

    // Family related

    public boolean isFamilyHead() {
        return isFamilyHead;
    }

    private Map<String, String> generateNickName(Map<String, String> familyName,
        Map<String, String> givenName) {
        // Implement your logic to generate nicknames in multiple languages
        // This example just concatenates the values with a space
        return Map.of(
            "en", givenName.get("en") + " " + familyName.get("en"),
            "jp", familyName.get("jp") + " " + givenName.get("jp")
        );
    }

}