package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.models.Section;

import java.util.List;
import java.util.Optional;

public interface SectionService {

    Optional<Section> getSectionById(Long id, String traceId);

    Section createSection(Section section);



    boolean deleteSection(Long id);

    Section updateSection(Long id, Section updatedSection, String traceId);


    List<Section> getSectionsByGeologicalClassCode(String code);

}
