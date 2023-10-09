package com.testing.geologicalsectionsapi.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.services.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SectionControllerTest {

    @InjectMocks
    private SectionController controller;

    @Mock
    private SectionService sectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSection() {
        // Mock the service response
        Section createdSection = new Section();
        when(sectionService.createSection(any())).thenReturn(createdSection);

        // Call the controller method
        Section result = controller.createSection(new Section(), "traceId", "channel", "user");

        // Verify the service method was called and check the result
        verify(sectionService).createSection(any());
        assertEquals(createdSection, result);
    }

    @Test
    void testGetSectionById() {
        // Mock the service response
        Section section = new Section();
        when(sectionService.getSectionById(anyLong(), anyString())).thenReturn(section);

        // Call the controller method
        Section result = controller.getSectionById(1L, "traceId", "channel", "user");

        // Verify the service method was called and check the result
        verify(sectionService).getSectionById(anyLong(), anyString());
        assertEquals(section, result);
    }

    @Test
    void testUpdateSection() {
        // Mock the service response
        Long id = 1L;
        Section updatedSection = new Section();
        when(sectionService.updateSection(eq(id), any(), anyString())).thenReturn(updatedSection);

        // Call the controller method
        Section result = controller.updateSection(id, new Section(), "traceId", "channel", "user");

        // Verify the service method was called and check the result
        verify(sectionService).updateSection(eq(id), any(), anyString());
        assertEquals(updatedSection, result);
    }

    @Test
    void testDeleteSection() {
        // Call the controller method
        controller.deleteSection(1L, "traceId", "channel", "user");

        // Verify the service method was called
        verify(sectionService).deleteSection(1L);
    }

    @Test
    void testGetSectionsByGeologicalClassCode() {
        // Mock the service response
        List<Section> sections = new ArrayList<>();
        Section section = new Section();
        section.setName("Section N");
        section.setId(4L);
        sections.add(section);

        when(sectionService.getSectionsByGeologicalClassCode(anyString())).thenReturn(sections);

        // Call the controller method
        ResponseEntity<List<Section>> response = controller.getSectionsByGeologicalClassCode("GCNM", "traceId", "channel", "user");

        // Verify the service method was called and check the response
        verify(sectionService).getSectionsByGeologicalClassCode("GCNM");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sections, response.getBody());
    }
}
