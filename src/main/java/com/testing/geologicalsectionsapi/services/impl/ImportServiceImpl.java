package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.dto.XlsImportDto;
import com.testing.geologicalsectionsapi.models.JobStatus;
import com.testing.geologicalsectionsapi.models.JobStatusEnum;
import com.testing.geologicalsectionsapi.models.JobType;
import com.testing.geologicalsectionsapi.repositories.JobStatusRepository;
import com.testing.geologicalsectionsapi.services.DataImportService;
import com.testing.geologicalsectionsapi.services.ImportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ImportServiceImpl extends CommonService implements ImportService  {

    final Logger logger = LoggerFactory.getLogger("Application");


    private final JobStatusRepository jobStatusRepository;

    private final DataImportService dataImportService;

    public ImportServiceImpl(JobStatusRepository jobStatusRepository, DataImportService dataImportService) {
        this.jobStatusRepository = jobStatusRepository;
        this.dataImportService = dataImportService;
    }

    @Async
    @Override
    public CompletableFuture<Long> importDataAsync(MultipartFile file) {
        try {
            // Save the job status as "IN PROGRESS"
            JobStatus jobStatus = new JobStatus(JobType.IMPORT, JobStatusEnum.IN_PROGRESS);
            jobStatus = jobStatusRepository.save(jobStatus);
            Long jobId = jobStatus.getId();

            List<XlsImportDto> importedData = parseImportedData(file);

            // Implement logic to save the imported data to your database
            // Use DataImportService to map and save data
            dataImportService.mapAndSaveData(importedData);

            // Update the job status as "DONE" and save the count of saved records
            jobStatus.setStatus(JobStatusEnum.DONE);
            jobStatus.setResult(importedData.size() + " records imported successfully.");
            jobStatusRepository.save(jobStatus);

            return CompletableFuture.completedFuture(jobId);
        } catch (DataIntegrityViolationException e) {
            // If there's a data integrity violation, update the job status as "ERROR"
            JobStatus jobStatus = new JobStatus(JobType.IMPORT, JobStatusEnum.ERROR);
            jobStatus.setResult("Data integrity violation: " + e.getMessage());
            jobStatusRepository.save(jobStatus);
            return CompletableFuture.completedFuture(-1L);
        } catch (Exception e) {
            // Handle other exceptions here, e.g., log the error and update job status as "ERROR"
            JobStatus jobStatus = new JobStatus(JobType.IMPORT, JobStatusEnum.ERROR);
            jobStatus.setResult("Import failed: " + e.getMessage());
            jobStatusRepository.save(jobStatus);
            // Additional logging
            logger.error("An error occurred during import: " + e.getMessage(), e);
            return CompletableFuture.completedFuture(-1L);
        }
    }


    private List<XlsImportDto> parseImportedData(MultipartFile file) throws IOException {
        List<XlsImportDto> importedData = new ArrayList<>();

        try (Workbook workbook = getWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            boolean isHeaderRow = true;
            List<String> columnHeaders = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isHeaderRow) {
                    // Collect column headers from the header row
                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        Cell cell = row.getCell(i);
                        columnHeaders.add(cell.getStringCellValue());
                    }
                    isHeaderRow = false;
                } else {
                    // Process data rows
                    XlsImportDto importDto = new XlsImportDto();
                    Map<String, String> classData = new HashMap<>();

                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        Cell cell = row.getCell(i);
                        String header = columnHeaders.get(i);
                        String cellValue = cell != null ? cell.getStringCellValue() : null;

                        if (cellValue != null && !header.equals("Section name")) {
                            // Use column headers other than "Section name" to populate class data
                            classData.put(header, cellValue);
                        } else if (header.equals("Section name")) {
                            importDto.setSectionName(cellValue);
                        }
                    }

                    importDto.setClassData(classData);
                    importedData.add(importDto);
                }
            }
        } catch (Exception e) {
            // Handle any exceptions that occur when opening the workbook
            logger.error("Error while opening the workbook: " + e.getMessage());
        }

        return importedData;
    }


    private Workbook getWorkbook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".XLS"))) {
            return new HSSFWorkbook(file.getInputStream()); // For .xls (OLE2) files
        } else if (fileName != null && (fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX"))) {
            return new XSSFWorkbook(file.getInputStream()); // For .xlsx (OOXML) files
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }


    @Override
    public String getStatusByIdAndType(String id) {
        JobStatus jobStatus = jobStatusRepository.findByIdAndJobType(Long.parseLong(id), JobType.IMPORT).orElse(null);

        if (jobStatus == null) {
            return JobType.IMPORT + " job with ID " + id + " not found";
        }

        String status = jobStatus.getStatus().name();
        String result = jobStatus.getResult();

        return JobType.IMPORT + " job ID: " + id + ", Status: " + status + ", Result: " + result;
    }
}





