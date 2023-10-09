package com.testing.geologicalsectionsapi.repositories;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long>  {


    Optional<GeologicalClass> findByCode(String code);

    List<GeologicalClass> findByName(String name);

    // Custom method to delete a GeologicalClass by its ID
    void deleteById(Long id);

    // Custom method to update a GeologicalClass
    // Spring Data JPA will automatically generate an UPDATE SQL query
    GeologicalClass save(GeologicalClass geologicalClass);
}
