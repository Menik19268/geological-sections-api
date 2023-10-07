package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.models.GeologicalClass;

import java.util.List;
import java.util.Optional;

public interface GeologicalClassService {

    GeologicalClass createGeologicalClass(GeologicalClass geologicalClass);

    Optional<GeologicalClass> getGeologicalClassById(Long id);

    boolean deleteGeologicalClass(Long id);

    GeologicalClass updateGeologicalClass(Long id, GeologicalClass updatedGeologicalClass);

    List<GeologicalClass> getGeologicalClassesByName(String name);

}
