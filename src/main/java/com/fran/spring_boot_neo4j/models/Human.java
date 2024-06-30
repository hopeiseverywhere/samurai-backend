package com.fran.spring_boot_neo4j.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fran.spring_boot_neo4j.models.enums.BirthSex;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * Represents a human
 */
@Data
public abstract class Human {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue
    private Long id;

    // Names
    @Property("givenName")
    private String givenNameJson;
    @Property("familyName")
    private String familyNameJson;
    @Property("nickName")
    private String nickNameJson;

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
    @Property("uji")
    private String ujiJson;

    /**
     * 八色の姓
     */
    @Property("kabane")
    private String kabaneJson;

    public Human() {
        this.identifier = UUID.randomUUID().toString();
    }

    // Conversion methods
    private String convertMapToJson(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting map to JSON", e);
        }
    }

    private Map<String, String> convertJsonToMap(String json) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to map", e);
        }
    }

    // Getters and setters for Map fields
    public Map<String, String> getGivenName() {
        return convertJsonToMap(givenNameJson);
    }

    public void setGivenName(Map<String, String> givenName) {
        this.givenNameJson = convertMapToJson(givenName);
    }

    public Map<String, String> getFamilyName() {
        return convertJsonToMap(familyNameJson);
    }

    public void setFamilyName(Map<String, String> familyName) {
        this.familyNameJson = convertMapToJson(familyName);
    }

    public Map<String, String> getNickName() {
        return convertJsonToMap(nickNameJson);
    }

    public void setNickName(Map<String, String> nickName) {
        this.nickNameJson = convertMapToJson(nickName);
    }

    public Map<String, String> getUji() {
        return convertJsonToMap(ujiJson);
    }

    public void setUji(Map<String, String> uji) {
        this.ujiJson = convertMapToJson(uji);
    }

    public Map<String, String> getKabane() {
        return convertJsonToMap(kabaneJson);
    }

    public void setKabane(Map<String, String> kabane) {
        this.kabaneJson = convertMapToJson(kabane);
    }
}