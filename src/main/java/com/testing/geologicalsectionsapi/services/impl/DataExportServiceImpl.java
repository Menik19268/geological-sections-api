package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import com.testing.geologicalsectionsapi.services.DataExportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DataExportServiceImpl implements DataExportService {

    private final SectionRepository sectionRepository;

    public DataExportServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void exportDataToExcel(String filePath) throws IOException {


        // Load data from the database (assuming you have data stored in the database)
        List<Section> sections = sectionRepository.findAll(); // Retrieve all sections

        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Geological Data");

        int rowNum = 0;

        // Determine the maximum number of classes across all sections
        int maxClasses = sections.stream()
                .mapToInt(section -> section.getGeologicalClasses().size())
                .max()
                .orElse(0);

        // Create header cells for section names, class names, and class codes dynamically based on maxClasses
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Section name");

        for (int i = 1; i <= maxClasses; i++) {
            headerRow.createCell(i * 2 - 1).setCellValue("Class " + i + " name");
            headerRow.createCell(i * 2).setCellValue("Class " + i + " code");
        }

        // Iterate through the sections and their geological classes
        for (Section section : sections) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(section.getName());

            List<GeologicalClass> geologicalClasses = section.getGeologicalClasses();

            // Create separate lists to store class names and codes in the correct order
            List<String> classNameList = new ArrayList<>();
            List<String> classCodeList = new ArrayList<>();

            // Iterate through the geological classes of the section
            for (int i = 0; i < geologicalClasses.size(); i++) {
                if (i < geologicalClasses.size()) {
                    GeologicalClass geologicalClass = geologicalClasses.get(i);
                    classNameList.add(geologicalClass.getName());
                    classCodeList.add(geologicalClass.getCode());
                } else {
                    // Fill in empty cells if there are no more geological classes
                    classNameList.add("");
                    classCodeList.add("");
                }
            }

            // Create a custom comparator to sort classNameList and classCodeList
            Comparator<String> comparator = (str1, str2) -> {
                String digit1 = str1.replaceAll("\\D", "");
                String digit2 = str2.replaceAll("\\D", "");
                return Integer.compare(Integer.parseInt(digit1), Integer.parseInt(digit2));
            };

            // Sort classNameList and classCodeList using the custom comparator
            Collections.sort(classNameList, comparator);
            Collections.sort(classCodeList, comparator);

            // Fill in the row with class names and codes in the correct order
            int cellIndex = 1;
            for (int i = 0; i < classNameList.size() || i < classCodeList.size(); i++) {
                if (i < classNameList.size()) {
                    row.createCell(cellIndex++).setCellValue(classNameList.get(i));
                } else {
                    row.createCell(cellIndex++).setCellValue(""); // Fill in empty cell if no more className
                }

                if (i < classCodeList.size()) {
                    row.createCell(cellIndex++).setCellValue(classCodeList.get(i));
                } else {
                    row.createCell(cellIndex++).setCellValue(""); // Fill in empty cell if no more classCode
                }
            }
        }

        // Write the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        // Close the workbook to release resources
        workbook.close();


    }

}

