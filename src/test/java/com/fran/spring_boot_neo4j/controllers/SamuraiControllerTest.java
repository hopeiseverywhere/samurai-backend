package com.fran.spring_boot_neo4j.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.objects.SamuraiDTO;
import com.fran.spring_boot_neo4j.requests.AddRelationshipRequest;
import com.fran.spring_boot_neo4j.requests.CreateSamuraiRequest;
import com.fran.spring_boot_neo4j.services.SamuraiService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SamuraiControllerTest {

    @Mock
    private SamuraiService samuraiService;

    @InjectMocks
    private SamuraiController samuraiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSamuraiByIdentifier() {
        String identifier = "123";
        Samurai samurai = new Samurai();
        samurai.setGivenName("Taro");
        samurai.setFamilyName("Yamada");
        samurai.setBirthDate(LocalDate.of(1500, 1, 1));
        samurai.setDeathDate(LocalDate.of(1570, 12, 31));

        when(samuraiService.getSamuraiByIdentifier(identifier)).thenReturn(samurai);

        ResponseEntity<SamuraiDTO> response = samuraiController.getSamuraiByIdentifier(identifier);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Taro", response.getBody().getGivenName());
        assertEquals("Yamada", response.getBody().getFamilyName());
        assertEquals(LocalDate.of(1500, 1, 1), response.getBody().getBirthDate());
        assertEquals(LocalDate.of(1570, 12, 31), response.getBody().getDeathDate());

        verify(samuraiService, times(1)).getSamuraiByIdentifier(identifier);
    }

    @Test
    void testCreateSamurai() {
        CreateSamuraiRequest request = new CreateSamuraiRequest();
        request.setGivenName("Hanzo");
        request.setFamilyName("Hattori");

        Samurai createdSamurai = new Samurai();
        createdSamurai.setGivenName("Hanzo");
        createdSamurai.setFamilyName("Hattori");

        when(samuraiService.createSamurai(request)).thenReturn(createdSamurai);

        ResponseEntity<Samurai> response = samuraiController.createSamurai(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Hanzo", response.getBody().getGivenName());
        assertEquals("Hattori", response.getBody().getFamilyName());

        verify(samuraiService, times(1)).createSamurai(request);
    }

    @Test
    void testDeleteSamurai() {
        String identifier = "456";

        ResponseEntity<String> response = samuraiController.deleteSamurai(identifier);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samurai successfully deleted", response.getBody());

        verify(samuraiService, times(1)).deleteSamurai(identifier);
    }

    @Test
    void testAddParentChildRelationshipWithRequestBody() {
        AddRelationshipRequest request = new AddRelationshipRequest();
        request.setParentIdentifier("parent123");
        request.setChildIdentifier("child123");
        request.setRelationshipType("BIOLOGICAL");

        ResponseEntity<String> response = samuraiController.addParentChildRelationship(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Relationship successfully added", response.getBody());

        verify(samuraiService, times(1)).addParentChildRelationship(request.getParentIdentifier(),
            request.getChildIdentifier(), request.getRelationshipType());
    }

    @Test
    void testAddParentChildRelationshipWithPathVariable() {
        String parentIdentifier = "parent123";
        String childIdentifier = "child123";
        String relationshipType = "BIOLOGICAL";

        Samurai parent = new Samurai();
        Samurai child = new Samurai();

        when(samuraiService.getSamuraiByIdentifier(parentIdentifier)).thenReturn(parent);
        when(samuraiService.getSamuraiByIdentifier(childIdentifier)).thenReturn(child);

        ResponseEntity<String> response = samuraiController.addParentChildRelationship(
            parentIdentifier, childIdentifier, relationshipType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Relationship successfully added", response.getBody());

        verify(samuraiService, times(1)).addParentChildRelationship(parentIdentifier,
            childIdentifier, relationshipType);
    }

    @Test
    void testGetSamuraiOffspring() {
        String identifier = "123";
        SamuraiDTO samuraiDTO = new SamuraiDTO("identifier", "Taro", "Yamada");

        when(samuraiService.getSamuraiTree(identifier)).thenReturn(samuraiDTO);

        ResponseEntity<SamuraiDTO> response = samuraiController.getSamuraiOffspring(identifier);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(samuraiDTO, response.getBody());

        verify(samuraiService, times(1)).getSamuraiTree(identifier);
    }
}