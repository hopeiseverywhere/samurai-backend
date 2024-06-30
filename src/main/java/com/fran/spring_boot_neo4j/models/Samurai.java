package com.fran.spring_boot_neo4j.models;

import com.fran.spring_boot_neo4j.models.enums.ClanHeritageStatus;
import com.fran.spring_boot_neo4j.models.enums.SocialStatus;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Represents a samurai entity in the Neo4j database.
 */
@NodeEntity
public class Samurai extends Human {

    @Setter @Getter
    private SocialStatus socialStatus;
    @Getter @Setter
    private ClanHeritageStatus clanHeritageStatus;


    public Samurai() {
    }

    public void setClanStatus(ClanHeritageStatus clanHeritageStatus) {
        this.clanHeritageStatus = clanHeritageStatus;
    }



}
