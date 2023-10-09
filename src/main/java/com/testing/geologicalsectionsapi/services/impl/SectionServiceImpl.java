package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.exception.CustomRequestException;
import com.testing.geologicalsectionsapi.models.GeologicalClass;
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

    @Override
    public Optional<Section> getSectionById(Long id, String traceId) {
        return sectionRepository.findById(id);

    }

    @Override
    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section updateSection(Long id, Section updatedSection, String traceId) {

        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException(traceId));

        existingSection.setName(updatedSection.getName());

        return sectionRepository.save(existingSection);
    }


    @Override
    public boolean deleteSection(Long id) {
        // Check if the class exists before deleting
        if (sectionRepository.existsById(id)) {
            sectionRepository.deleteById(id);
            return true; // Deletion successful
        } else {
            return false; // Class with the given ID not found
        }
    }


    @Override
    public List<Section> getSectionsByGeologicalClassCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }

}
