package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.services.impl.GeologicalClassServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GeologicalClassControllerTest {

    @InjectMocks
    private GeologicalClassController controller;

    @Mock
    private GeologicalClassServiceImpl geologicalClassService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGeologicalClass() {
        // Mock the service response
        GeologicalClass createdGeologicalClass = new GeologicalClass();
        when(geologicalClassService.createGeologicalClass(any())).thenReturn(createdGeologicalClass);

        // Call the controller method
        ResponseEntity<GeologicalClass> response = controller.createGeologicalClass(new GeologicalClass(), "traceId", "channel", "user");

        // Verify the service method was called and check the response
        verify(geologicalClassService).createGeologicalClass(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdGeologicalClass, response.getBody());
    }

    @Test
    void testGetGeologicalClassById() {
        // Mock the service response
        GeologicalClass geologicalClass = new GeologicalClass();
        when(geologicalClassService.getGeologicalClassById(anyLong())).thenReturn(Optional.of(geologicalClass));

        // Call the controller method
        ResponseEntity<GeologicalClass> response = controller.getGeologicalClassById(1L, "traceId", "channel", "user");

        // Verify the service method was called and check the response
        verify(geologicalClassService).getGeologicalClassById(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(geologicalClass, response.getBody());
    }

    @Test
    void testUpdateGeologicalClass() {
        // Mock the service response
        Long id = 1L;
        GeologicalClass updatedGeologicalClass = new GeologicalClass();
        when(geologicalClassService.updateGeologicalClass(eq(id), any())).thenReturn(updatedGeologicalClass);

        // Call the controller method
        ResponseEntity<GeologicalClass> response = controller.updateGeologicalClass(id, new GeologicalClass(), "traceId", "channel", "user");

        // Verify the service method was called and check the response
        verify(geologicalClassService).updateGeologicalClass(eq(id), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGeologicalClass, response.getBody());
    }

    @Test
    void testDeleteGeologicalClass() {
        // Mock the service response
        Long id = 1L;
        when(geologicalClassService.deleteGeologicalClass(eq(id))).thenReturn(true);

        // Call the controller method
        ResponseEntity<Void> response = controller.deleteGeologicalClass(id, "traceId", "channel", "user");

        // Verify the service method was called and check the response
        verify(geologicalClassService).deleteGeologicalClass(eq(id));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
