package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.exception.CustomRequestException;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import com.testing.geologicalsectionsapi.services.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;


    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


    public Section getSectionById(Long id, String traceId) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException(traceId));

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
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }


    @Override
    public List<Section> getSectionsByGeologicalClassCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }

}
