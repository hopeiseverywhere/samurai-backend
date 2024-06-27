package com.fran.spring_boot_neo4j.requests;

public class AddRelationshipRequest {
    private String parentIdentifier;
    private String childIdentifier;
    private String relationshipType;

    // Getters and Setters
    public String getParentIdentifier() {
        return parentIdentifier;
    }

    public void setParentIdentifier(String parentIdentifier) {
        this.parentIdentifier = parentIdentifier;
    }

    public String getChildIdentifier() {
        return childIdentifier;
    }

    public void setChildIdentifier(String childIdentifier) {
        this.childIdentifier = childIdentifier;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}
