package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.ClanHeritageStatus;
import com.fran.spring_boot_neo4j.models.enums.SocialStatus;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Represents a samurai entity in the Neo4j database.
 */
@NodeEntity
public class Samurai extends Human {

    private SocialStatus socialStatus;
    private ClanHeritageStatus clanHeritageStatus;


    public Samurai() {
    }

    public void setClanStatus(ClanHeritageStatus clanHeritageStatus) {
        this.clanHeritageStatus = clanHeritageStatus;
    }

    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus;
    }

    public SocialStatus getSocialStatus() {
        return socialStatus;
    }

    public ClanHeritageStatus getClanStatus() {
        return clanHeritageStatus;
    }
}
