package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.exception.ValidationException;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import com.testing.geologicalsectionsapi.services.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;


    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


//    public Section createSection(Section section) {
//        // Implement logic to create a section
//        // You can use sectionRepository.save(section) for Spring Data JPA
//
//        // Example:
//        return sectionRepository.save(section);
//    }

    public Section getSectionById(Long id, String traceId) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ValidationException(traceId));

    }


    @Override
    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section updateSection(Long id, Section updatedSection, String traceId) {

        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ValidationException(traceId));

        existingSection.setName(updatedSection.getName());

        return sectionRepository.save(existingSection);
    }

    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }


    @Override
    public List<Section> getSectionsByGeologicalClassCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }

}
