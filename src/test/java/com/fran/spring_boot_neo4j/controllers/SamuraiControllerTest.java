package com.fran.spring_boot_neo4j.controllers;

import com.fran.spring_boot_neo4j.models.Samurai;
import com.fran.spring_boot_neo4j.objects.SamuraiDTO;
import com.fran.spring_boot_neo4j.requests.CreateSamuraiRequest;
import com.fran.spring_boot_neo4j.services.SamuraiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        // Arrange
        String identifier = "123";
        Samurai samurai = new Samurai();
        samurai.setGivenName("Taro");
        samurai.setFamilyName("Yamada");
        samurai.setBirthDate(LocalDate.of(1500, 1, 1));
        samurai.setDeathDate(LocalDate.of(1570, 12, 31));

        when(samuraiService.getSamuraiByIdentifier(identifier)).thenReturn(samurai);

        // Act
        ResponseEntity<SamuraiDTO> response = samuraiController.getSamuraiByIdentifier(identifier);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Taro", response.getBody().getGivenName());
        assertEquals("Yamada", response.getBody().getFamilyName());
        assertEquals(LocalDate.of(1500, 1, 1), response.getBody().getBirthDate());
        assertEquals(LocalDate.of(1570, 12, 31), response.getBody().getDeathDate());

        verify(samuraiService, times(1)).getSamuraiByIdentifier(identifier);
    }

    @Test
    void testCreateSamurai() {
        // Arrange
        CreateSamuraiRequest request = new CreateSamuraiRequest();
        request.setGivenName("Hanzo");
        request.setFamilyName("Hattori");

        Samurai createdSamurai = new Samurai();
        createdSamurai.setGivenName("Hanzo");
        createdSamurai.setFamilyName("Hattori");

        when(samuraiService.createSamurai(request)).thenReturn(createdSamurai);

        // Act
        ResponseEntity<Samurai> response = samuraiController.createSamurai(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Hanzo", response.getBody().getGivenName());
        assertEquals("Hattori", response.getBody().getFamilyName());

        verify(samuraiService, times(1)).createSamurai(request);
    }

    @Test
    void testDeleteSamurai() {
        // Arrange
        String identifier = "456";

        // Act
        ResponseEntity<String> response = samuraiController.deleteSamurai(identifier);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samurai successfully deleted", response.getBody());

        verify(samuraiService, times(1)).deleteSamurai(identifier);
    }
}