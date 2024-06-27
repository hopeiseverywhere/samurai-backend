package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.ClanStatus;
import com.fran.spring_boot_neo4j.models.enums.SocialStatus;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Represents a samurai entity in the Neo4j database.
 */
@Node
public class Samurai extends Human {

    private SocialStatus socialStatus;
    private ClanStatus clanStatus;

    public Samurai() {
    }

    public void setClanStatus(ClanStatus clanStatus) {
        this.clanStatus = clanStatus;
    }

    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus;
    }

    public SocialStatus getSocialStatus() {
        return socialStatus;
    }

    public ClanStatus getClanStatus() {
        return clanStatus;
    }
}
