package com.fran.spring_boot_neo4j.requests;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import lombok.Data;

/**
 * Request object for creating a new samurai.
 */
@Data
public class CreateSamuraiRequest {

    private String givenNameEN;
    private String familyNameEN;
    private String nickNameEN;
    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String parentIdentifier;
    private String relationshipType;

    private boolean isFamilyHead;

    private String clanNameEN;

    /**
     * 氏
     */
    private String ujiEN;

    /**
     * 八色の姓
     */
    private String kabaneEN;

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
        this.givenNameEN = givenName;
        this.familyNameEN = familyName;
        this.nickNameEN = familyName + " " + givenName;
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
        this.givenNameEN = givenName;
        this.familyNameEN = familyName;
        this.nickNameEN = nickName;
    }

    // Family related

    public boolean isFamilyHead() {
        return isFamilyHead;
    }

}