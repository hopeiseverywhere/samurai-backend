package com.fran.spring_boot_neo4j.models;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

/**
 * Represents a clan entity in the Neo4j database.
 */
@NodeEntity
public class Clan {

    @Id
    @GeneratedValue
    private Long id;

    private String identifier;


    private String clanName;

    public Clan() {
    }

    public Clan(String clanName) {
        this.clanName = clanName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }
}
