package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * Represents a human
 */
@Data
public abstract class Human {

    @Id
    @GeneratedValue
    private Long id;

    // Names
    private String givenNameEN;
    private String familyNameEN;
    private String nickNameEN;

    private String givenNameJP;
    private String familyNameJP;
    private String nickNameJP;

    // Sex and birthdate
    private BirthSex sex;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @Relationship(type = "BELONGS_TO")
    private Clan clan;

    // Unique UUID identifier
    private String identifier;

    private boolean isFamilyHead;

    // Myoji related
    /**
     * 氏
     */
    private String ujiEN;
    private String ujiJP;

    /**
     * 八色の姓
     */
    private String kabaneEN;
    private String kabaneJP;

    public Human() {
    }


}