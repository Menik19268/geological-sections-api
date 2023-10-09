package com.testing.geologicalsectionsapi.services.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.testing.geologicalsectionsapi.exception.CustomRequestException;
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

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionServiceImpl sectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSectionById() {
        Long sectionId = 1L;
        Section sectionToReturn = new Section();
        sectionToReturn.setName("Section N");
        sectionToReturn.setId(4L);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(sectionToReturn));

        Section retrievedSection = sectionService.getSectionById(sectionId, "traceId");

        assertNotNull(retrievedSection);
        assertEquals("Section N", retrievedSection.getName());

        verify(sectionRepository, times(1)).findById(sectionId);
    }

    @Test
    void testGetSectionByIdNotFound() {
        Long sectionId = 1L;
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        assertThrows(CustomRequestException.class, () -> {
            sectionService.getSectionById(sectionId, "traceId");
        });

        verify(sectionRepository, times(1)).findById(sectionId);
    }

    @Test
    void testCreateSection() {

        Section sectionToCreate = new Section();
        sectionToCreate.setName("TestSection");


        when(sectionRepository.save(sectionToCreate)).thenReturn(sectionToCreate);

        Section createdSection = sectionService.createSection(sectionToCreate);

        assertNotNull(createdSection);
        assertEquals("TestSection", createdSection.getName());

        verify(sectionRepository, times(1)).save(sectionToCreate);
    }

    @Test
    void testUpdateSection() {
        Long sectionId = 1L;


        Section existingSection = new Section();
        existingSection.setName("Section N");
        existingSection.setId(4L);

        Section updatedSection = new Section();
        updatedSection.setName("Section N");
        updatedSection.setId(4L);


        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(existingSection));
        when(sectionRepository.save(existingSection)).thenReturn(existingSection);

        Section updated = sectionService.updateSection(sectionId, updatedSection, "traceId");

        assertNotNull(updated);
        assertEquals("Section N", updated.getName());

        verify(sectionRepository, times(1)).findById(sectionId);
        verify(sectionRepository, times(1)).save(existingSection);
    }

    @Test
    void testUpdateSectionNotFound() {
        Long sectionId = 1L;

        Section updatedSection = new Section();
        updatedSection.setName("Section N");
        updatedSection.setId(4L);


        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        assertThrows(CustomRequestException.class, () -> {
            sectionService.updateSection(sectionId, updatedSection, "traceId");
        });

        verify(sectionRepository, times(1)).findById(sectionId);
        verify(sectionRepository, never()).save(any());
    }

    @Test
    void testDeleteSection() {
        Long sectionId = 1L;
        sectionService.deleteSection(sectionId);

        verify(sectionRepository, times(1)).deleteById(sectionId);
    }

    @Test
    void testGetSectionsByGeologicalClassCode() {
        String classCode = "TestClassCode";
        List<Section> mockSections = new ArrayList<>();

        Section section1 = new Section();
        section1.setName("Section1");
        section1.setId(4L);

        Section section2 = new Section();
        section2.setName("Section2");
        section2.setId(4L);

        mockSections.add(section1);
        mockSections.add(section2);

        when(sectionRepository.findByGeologicalClassesCode(classCode)).thenReturn(mockSections);

        List<Section> retrievedSections = sectionService.getSectionsByGeologicalClassCode(classCode);

        assertEquals(2, retrievedSections.size());
        assertEquals("Section1", retrievedSections.get(0).getName());
        assertEquals("Section2", retrievedSections.get(1).getName());

        verify(sectionRepository, times(1)).findByGeologicalClassesCode(classCode);
    }
}
