package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.SectionResponse;
import org.junit.jupiter.api.Test;

import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.services.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SectionControllerTest {

    @InjectMocks
    private SectionController sectionController;

    @Mock
    private SectionService sectionService;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSection() {
        // Arrange
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        Section section = new Section();
        section.setName("Test Section");

        when(sectionService.createSection(section)).thenReturn(section);

        // Act
        ResponseEntity<SectionResponse> response = sectionController.createSection(section, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        SectionResponse sectionResponse = response.getBody();
        assertEquals(section.getId(), sectionResponse.getId());
    }


    @Test
    void testGetSectionById() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        Section section = new Section();
        section.setId(sectionId);

        when(sectionService.getSectionById(sectionId, traceId)).thenReturn(Optional.of(section));

        // Act
        ResponseEntity<Section> response = sectionController.getSectionById(sectionId, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(section, response.getBody());
    }

    @Test
    void testGetSectionByIdNotFound() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";

        when(sectionService.getSectionById(sectionId, traceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Section> response = sectionController.getSectionById(sectionId, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateSection() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        Section updatedSection = new Section();
        updatedSection.setId(sectionId);
        updatedSection.setName("Updated Section");

        when(sectionService.updateSection(sectionId, updatedSection, traceId)).thenReturn(updatedSection);

        // Act
        ResponseEntity<Section> response = sectionController.updateSection(sectionId, updatedSection, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedSection, response.getBody());
    }

    @Test
    void testUpdateSectionNotFound() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        Section updatedSection = new Section();
        updatedSection.setId(sectionId);
        updatedSection.setName("Updated Section");

        when(sectionService.updateSection(sectionId, updatedSection, traceId)).thenReturn(null);

        // Act
        ResponseEntity<Section> response = sectionController.updateSection(sectionId, updatedSection, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteSection() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";

        when(sectionService.deleteSection(sectionId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = sectionController.deleteSection(sectionId, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteSectionNotFound() {
        // Arrange
        Long sectionId = 1L;
        String traceId = "123";
        String channel = "channel";
        String user = "user";

        when(sectionService.deleteSection(sectionId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = sectionController.deleteSection(sectionId, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetSectionsByGeologicalClassCode() {
        // Arrange
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        String code = "ABC";
        Section section = new Section();
        section.setId(1L);
        List<Section> sections = Collections.singletonList(section);

        when(sectionService.getSectionsByGeologicalClassCode(code)).thenReturn(sections);

        // Act
        ResponseEntity<List<Section>> response = sectionController.getSectionsByGeologicalClassCode(code, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sections, response.getBody());
    }

    @Test
    void testGetSectionsByGeologicalClassCodeNotFound() {
        // Arrange
        String traceId = "123";
        String channel = "channel";
        String user = "user";
        String code = "ABC";

        when(sectionService.getSectionsByGeologicalClassCode(code)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Section>> response = sectionController.getSectionsByGeologicalClassCode(code, traceId, channel, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

