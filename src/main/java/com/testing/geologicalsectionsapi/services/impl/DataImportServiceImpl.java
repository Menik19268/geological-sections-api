package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.dto.XlsImportDto;
import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.GeologicalClassRepository;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import com.testing.geologicalsectionsapi.services.DataImportService;
import com.testing.geologicalsectionsapi.services.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DataImportServiceImpl implements DataImportService {

    final Logger logger = LoggerFactory.getLogger("Application");

    private final SectionRepository sectionRepository;
    private final GeologicalClassRepository geologicalClassRepository;

    public DataImportServiceImpl(SectionRepository sectionRepository, GeologicalClassRepository geologicalClassRepository) {
        this.sectionRepository = sectionRepository;
        this.geologicalClassRepository = geologicalClassRepository;
    }

    @Transactional
    public void mapAndSaveData(List<XlsImportDto> importedData) {
        for (XlsImportDto importDto : importedData) {
            try {
                // Map XlsImportDto to your entity classes (Section and GeologicalClass)
                Section section = new Section();
                section.setName(importDto.getSectionName());

                // Save the Section entity
                Section createdSection = sectionRepository.save(section);

                // Map and save GeologicalClass entities
                Map<String, String> classData = importDto.getClassData();

                for (Map.Entry<String, String> entry : classData.entrySet()) {
                    String header = entry.getKey();
                    String cellValue = entry.getValue();

                    if (header.contains("code")) {
                        // This header is for class code
                        String classNameHeader = header.replace(" code", " name");
                        String className = classData.get(classNameHeader);

                        if (className != null) {
                            GeologicalClass geologicalClass = new GeologicalClass();
                            geologicalClass.setName(className);
                            geologicalClass.setCode(cellValue);
                            geologicalClass.setSection(createdSection);

                            geologicalClassRepository.save(geologicalClass);
                        }
                    }
                }
            } catch (DataIntegrityViolationException e) {
                // Handle data integrity violations here, e.g., log the error
                logger.error("Data integrity violation while saving: " + e.getMessage());
                // You can choose to throw a custom exception or handle it as per your application's needs.
            } catch (Exception e) {
                // Handle other exceptions here, e.g., log the error
                logger.error("An error occurred while saving data: " + e.getMessage());
                // You can choose to throw a custom exception or handle it as per your application's needs.
            }
        }
    }

}
