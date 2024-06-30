package com.fran.spring_boot_neo4j.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * Represents a clan entity in the Neo4j database.
 */
@NodeEntity
@Data
public class Clan {

    @Id
    @GeneratedValue
    private Long id;

    private String identifier;

    @Property("clanName")
    private String clanNameJson;  // Store the map as a JSON string

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public Clan() {
        this.identifier = UUID.randomUUID().toString();
    }

    public Clan(Map<String, String> clanName) {
        this.clanNameJson = convertMapToJson(clanName);
        this.identifier = UUID.randomUUID().toString();
    }

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

    public Map<String, String> getClanName() {
        return convertJsonToMap(clanNameJson);
    }

    public void setClanName(Map<String, String> clanName) {
        this.clanNameJson = convertMapToJson(clanName);
    }

}
