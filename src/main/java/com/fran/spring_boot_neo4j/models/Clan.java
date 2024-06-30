package com.fran.spring_boot_neo4j.models;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

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

    private String clanNameEN;

    private String clanNameJP;

    public Clan() {
    }

    public Clan(String clanNameEn) {
        this.clanNameEN = clanNameEn;
    }


}
