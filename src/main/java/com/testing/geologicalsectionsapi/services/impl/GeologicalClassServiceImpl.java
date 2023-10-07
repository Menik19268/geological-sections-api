package com.testing.geologicalsectionsapi.services.impl;


import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.repositories.GeologicalClassRepository;
import com.testing.geologicalsectionsapi.services.GeologicalClassService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeologicalClassServiceImpl implements GeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;


    public GeologicalClassServiceImpl(GeologicalClassRepository geologicalClassRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
    }

    public GeologicalClass createGeologicalClass(GeologicalClass geologicalClass) {
        // Add any additional logic here if needed
        return geologicalClassRepository.save(geologicalClass);
    }

    public Optional<GeologicalClass> getGeologicalClassById(Long id) {
        return geologicalClassRepository.findById(id);
    }

    public List<GeologicalClass> getAllGeologicalClasses() {
        return geologicalClassRepository.findAll();
    }

    public Optional<GeologicalClass> getGeologicalClassByCode(String code) {
        return geologicalClassRepository.findByCode(code);
    }

    public List<GeologicalClass> getGeologicalClassesByName(String name) {
        return geologicalClassRepository.findByName(name);
    }

    public GeologicalClass updateGeologicalClass(Long id, GeologicalClass updatedGeologicalClass) {
        Optional<GeologicalClass> existingClass = geologicalClassRepository.findById(id);
        if (existingClass.isPresent()) {
            GeologicalClass updatedClass = existingClass.get();
            updatedClass.setName(updatedGeologicalClass.getName());
            updatedClass.setCode(updatedGeologicalClass.getCode());

            return geologicalClassRepository.save(updatedClass);
        } else {
            // Handle the case where the class with the given ID is not found
            return null;
        }
    }

    public boolean deleteGeologicalClass(Long id) {
        // Check if the class exists before deleting
        if (geologicalClassRepository.existsById(id)) {
            geologicalClassRepository.deleteById(id);
            return true; // Deletion successful
        } else {
            return false; // Class with the given ID not found
        }
    }
}
