package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.models.Section;

import java.util.List;

public interface SectionService {

    Section getSectionById(Long id, String traceId);

    Section createSection(Section section);

    void deleteSection(Long id);

    Section updateSection(Long id, Section updatedSection, String traceId);


    List<Section> getSectionsByGeologicalClassCode(String code);

}
