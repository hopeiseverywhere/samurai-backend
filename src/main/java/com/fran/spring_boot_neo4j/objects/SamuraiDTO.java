package com.fran.spring_boot_neo4j.objects;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * Data Transfer Object for samurai.
 */
@Data
public class SamuraiDTO {

    private String givenNameEN;
    private String familyNameEN;
    private String nickNameEN;
    private BirthSex sex;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String identifier;
    private String relationshipTypeWithParent;
    private List<SamuraiDTO> offspring = new ArrayList<>();

    private boolean isFamilyHead;

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given NameEN and family NameEN.
     *
     * @param givenNameEN  the given NameEN of the samurai
     * @param familyNameEN the family NameEN of the samurai
     */
    public SamuraiDTO(String givenNameEN, String familyNameEN) {
        this.givenNameEN = givenNameEN;
        this.familyNameEN = familyNameEN;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given NameEN, family NameEN, and death date.
     *
     * @param givenNameEN  the given NameEN of the samurai
     * @param familyNameEN the family NameEN of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(String givenNameEN, String familyNameEN, LocalDate deathDate) {
        this.givenNameEN = givenNameEN;
        this.familyNameEN = familyNameEN;
        this.deathDate = deathDate;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified given NameEN, family NameEN, birth date, and death date.
     *
     * @param givenNameEN  the given NameEN of the samurai
     * @param familyNameEN the family NameEN of the samurai
     * @param birthDate  the birthdate of the samurai
     * @param deathDate  the death date of the samurai
     */
    public SamuraiDTO(String givenNameEN, String familyNameEN, LocalDate birthDate, LocalDate deathDate) {
        this.givenNameEN = givenNameEN;
        this.familyNameEN = familyNameEN;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.nickNameEN = givenNameEN + " " + familyNameEN;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given NameEN, and family NameEN.
     *
     * @param identifier the identifier of the samurai
     * @param givenNameEN  the given NameEN of the samurai
     * @param familyNameEN the family NameEN of the samurai
     */
    public SamuraiDTO(String identifier, String givenNameEN, String familyNameEN) {
        this.identifier = identifier;
        this.givenNameEN = givenNameEN;
        this.familyNameEN = familyNameEN;
        this.nickNameEN = givenNameEN + " " + familyNameEN;
    }

    /**
     * Constructs a new {@code SamuraiDTO} with the specified identifier, given NameEN, family NameEN, and nickNameEN.
     *
     * @param identifier the identifier of the samurai
     * @param givenNameEN  the given NameEN of the samurai
     * @param familyNameEN the family NameEN of the samurai
     * @param nickNameEN   the nickNameEN of the samurai
     */
    public SamuraiDTO(String identifier, String givenNameEN, String familyNameEN, String nickNameEN) {
        this.identifier = identifier;
        this.givenNameEN = givenNameEN;
        this.familyNameEN = familyNameEN;
        this.nickNameEN = nickNameEN;
    }



    public void addOffspring(SamuraiDTO offspring) {
        this.offspring.add(offspring);
    }

    // Family related
    public boolean isFamilyHead() {
        return isFamilyHead;
    }

}