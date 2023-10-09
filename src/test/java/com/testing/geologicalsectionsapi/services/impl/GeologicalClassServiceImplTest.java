package com.testing.geologicalsectionsapi.services.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.repositories.GeologicalClassRepository;
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

public class GeologicalClassServiceImplTest {

    @Mock
    private GeologicalClassRepository geologicalClassRepository;

    @InjectMocks
    private GeologicalClassServiceImpl geologicalClassService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGeologicalClass() {
        GeologicalClass classToCreate = new GeologicalClass("TestName", "TestClassCode");
        when(geologicalClassRepository.save(classToCreate)).thenReturn(classToCreate);

        GeologicalClass createdClass = geologicalClassService.createGeologicalClass(classToCreate);

        assertNotNull(createdClass);
        assertEquals("TestName", createdClass.getName());
        assertEquals("TestClassCode", createdClass.getCode());

        verify(geologicalClassRepository, times(1)).save(classToCreate);
    }

    @Test
    void testGetGeologicalClassById() {
        Long classId = 1L;
        GeologicalClass classToReturn = new GeologicalClass("TestName", "TestClassCode");
        when(geologicalClassRepository.findById(classId)).thenReturn(Optional.of(classToReturn));

        Optional<GeologicalClass> retrievedClass = geologicalClassService.getGeologicalClassById(classId);

        assertTrue(retrievedClass.isPresent());
        assertEquals("TestName", retrievedClass.get().getName());
        assertEquals("TestClassCode", retrievedClass.get().getCode());

        verify(geologicalClassRepository, times(1)).findById(classId);
    }

    @Test
    void testGetAllGeologicalClasses() {
        List<GeologicalClass> mockClasses = new ArrayList<>();
        mockClasses.add(new GeologicalClass("Class1", "Code1"));
        mockClasses.add(new GeologicalClass("Class2", "Code2"));
        when(geologicalClassRepository.findAll()).thenReturn(mockClasses);

        List<GeologicalClass> allClasses = geologicalClassService.getAllGeologicalClasses();

        assertEquals(2, allClasses.size());
        assertEquals("Class1", allClasses.get(0).getName());
        assertEquals("Class2", allClasses.get(1).getName());

        verify(geologicalClassRepository, times(1)).findAll();
    }

    @Test
    void testGetGeologicalClassByCode() {
        String classCode = "TestClassCode";
        GeologicalClass classToReturn = new GeologicalClass("TestName", classCode);
        when(geologicalClassRepository.findByCode(classCode)).thenReturn(Optional.of(classToReturn));

        Optional<GeologicalClass> retrievedClass = geologicalClassService.getGeologicalClassByCode(classCode);

        assertTrue(retrievedClass.isPresent());
        assertEquals("TestName", retrievedClass.get().getName());
        assertEquals(classCode, retrievedClass.get().getCode());

        verify(geologicalClassRepository, times(1)).findByCode(classCode);
    }

    @Test
    void testGetGeologicalClassesByName() {
        String className = "TestClass";
        List<GeologicalClass> mockClasses = new ArrayList<>();
        mockClasses.add(new GeologicalClass(className, "Code1"));
        mockClasses.add(new GeologicalClass(className, "Code2"));
        when(geologicalClassRepository.findByName(className)).thenReturn(mockClasses);

        List<GeologicalClass> retrievedClasses = geologicalClassService.getGeologicalClassesByName(className);

        assertEquals(2, retrievedClasses.size());
        assertEquals(className, retrievedClasses.get(0).getName());
        assertEquals(className, retrievedClasses.get(1).getName());

        verify(geologicalClassRepository, times(1)).findByName(className);
    }

    @Test
    void testUpdateGeologicalClass() {
        Long classId = 1L;
        GeologicalClass existingClass = new GeologicalClass("OldName", "OldCode");
        GeologicalClass updatedClass = new GeologicalClass("NewName", "NewCode");
        when(geologicalClassRepository.findById(classId)).thenReturn(Optional.of(existingClass));
        when(geologicalClassRepository.save(existingClass)).thenReturn(existingClass);

        GeologicalClass updated = geologicalClassService.updateGeologicalClass(classId, updatedClass);

        assertNotNull(updated);
        assertEquals("NewName", updated.getName());
        assertEquals("NewCode", updated.getCode());

        verify(geologicalClassRepository, times(1)).findById(classId);
        verify(geologicalClassRepository, times(1)).save(existingClass);
    }

    @Test
    void testUpdateGeologicalClassNotFound() {
        Long classId = 1L;
        GeologicalClass updatedClass = new GeologicalClass("NewName", "NewCode");
        when(geologicalClassRepository.findById(classId)).thenReturn(Optional.empty());

        GeologicalClass updated = geologicalClassService.updateGeologicalClass(classId, updatedClass);

        assertNull(updated);

        verify(geologicalClassRepository, times(1)).findById(classId);
        verify(geologicalClassRepository, never()).save(any());
    }

    @Test
    void testDeleteGeologicalClass() {
        Long classId = 1L;
        when(geologicalClassRepository.existsById(classId)).thenReturn(true);

        boolean deleted = geologicalClassService.deleteGeologicalClass(classId);

        assertTrue(deleted);

        verify(geologicalClassRepository, times(1)).existsById(classId);
        verify(geologicalClassRepository, times(1)).deleteById(classId);
    }

    @Test
    void testDeleteGeologicalClassNotFound() {
        Long classId = 1L;
        when(geologicalClassRepository.existsById(classId)).thenReturn(false);

        boolean deleted = geologicalClassService.deleteGeologicalClass(classId);

        assertFalse(deleted);

        verify(geologicalClassRepository, times(1)).existsById(classId);
        verify(geologicalClassRepository, never()).deleteById(any());
    }
}
