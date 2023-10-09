package com.testing.geologicalsectionsapi.services.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.testing.geologicalsectionsapi.exception.CustomRequestException;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import com.testing.geologicalsectionsapi.exception.CustomRequestException;
import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionServiceImplTest {

    @InjectMocks
    private SectionServiceImpl sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSectionById() {
        // Arrange
        Long sectionId = 1L;
        Section section = new Section();
        section.setId(sectionId);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));

        // Act
        Optional<Section> result = sectionService.getSectionById(sectionId, "traceId");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sectionId, result.get().getId());
    }

    @Test
    void testGetSectionByIdNotFound() {
        // Arrange
        Long sectionId = 1L;

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act
        Optional<Section> result = sectionService.getSectionById(sectionId, "traceId");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateSection() {
        // Arrange
        Section section = new Section();

        when(sectionRepository.save(section)).thenReturn(section);

        // Act
        Section result = sectionService.createSection(section);

        // Assert
        assertNotNull(result);
        assertEquals(section, result);
    }

    @Test
    void testUpdateSection() {
        // Arrange
        Long sectionId = 1L;
        String updatedName = "Updated Section Name";
        Section existingSection = new Section();
        existingSection.setId(sectionId);
        Section updatedSection = new Section();
        updatedSection.setName(updatedName);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(existingSection));
        when(sectionRepository.save(existingSection)).thenReturn(existingSection);

        // Act
        Section result = sectionService.updateSection(sectionId, updatedSection, "traceId");

        // Assert
        assertNotNull(result);
        assertEquals(updatedName, result.getName());
    }

    @Test
    void testUpdateSectionNotFound() {
        // Arrange
        Long sectionId = 1L;
        Section updatedSection = new Section();

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomRequestException.class, () -> sectionService.updateSection(sectionId, updatedSection, "traceId"));
    }

    @Test
    void testDeleteSection() {
        // Arrange
        Long sectionId = 1L;

        when(sectionRepository.existsById(sectionId)).thenReturn(true);

        // Act
        boolean result = sectionService.deleteSection(sectionId);

        // Assert
        assertTrue(result);
        verify(sectionRepository, times(1)).deleteById(sectionId);
    }

    @Test
    void testDeleteSectionNotFound() {
        // Arrange
        Long sectionId = 1L;

        when(sectionRepository.existsById(sectionId)).thenReturn(false);

        // Act
        boolean result = sectionService.deleteSection(sectionId);

        // Assert
        assertFalse(result);
        verify(sectionRepository, never()).deleteById(sectionId);
    }

    @Test
    void testGetSectionsByGeologicalClassCode() {
        // Arrange
        String code = "ABC";
        List<Section> sections = new ArrayList<>();

        when(sectionRepository.findByGeologicalClassesCode(code)).thenReturn(sections);

        // Act
        List<Section> result = sectionService.getSectionsByGeologicalClassCode(code);

        // Assert
        assertNotNull(result);
        assertEquals(sections, result);
    }
}

