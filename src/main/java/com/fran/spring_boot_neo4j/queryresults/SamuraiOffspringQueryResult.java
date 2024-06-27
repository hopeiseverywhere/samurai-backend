package com.fran.spring_boot_neo4j.queryresults;

import com.fran.spring_boot_neo4j.models.Samurai;

/**
 * Custom query result class for samurai offspring.
 */
public class SamuraiOffspringQueryResult {

    private Samurai offspring;
    private String relationshipType;

    public SamuraiOffspringQueryResult() {
    }

    public Samurai getOffspring() {
        return offspring;
    }

    public void setOffspring(Samurai offspring) {
        this.offspring = offspring;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}