package com.fran.spring_boot_neo4j.queryresults;

import com.fran.spring_boot_neo4j.models.Samurai;
import lombok.Getter;
import lombok.Setter;

/**
 * Query result class for retrieving a samurai offspring and the relationship type.
 */
@Setter
@Getter
public class SamuraiOffspringQueryResult {

    private Samurai offspring;
    private String relationshipType;

    public SamuraiOffspringQueryResult() {
    }

}
