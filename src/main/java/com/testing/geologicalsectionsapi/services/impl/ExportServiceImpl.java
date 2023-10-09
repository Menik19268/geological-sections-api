package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.models.JobStatus;
import com.testing.geologicalsectionsapi.models.JobStatusEnum;
import com.testing.geologicalsectionsapi.models.JobType;
import com.testing.geologicalsectionsapi.repositories.JobStatusRepository;
import com.testing.geologicalsectionsapi.services.DataExportService;
import com.testing.geologicalsectionsapi.services.ExportService;
import com.testing.geologicalsectionsapi.util.FileNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ExportServiceImpl extends CommonService implements ExportService {

    private final JobStatusRepository jobStatusRepository;
    private final DataExportService dataExportService;
    private final FileNameGenerator fileNameGenerator;

    final Logger logger = LoggerFactory.getLogger("Application");


    public ExportServiceImpl(JobStatusRepository jobStatusRepository, DataExportService dataExportService, FileNameGenerator fileNameGenerator) {
        this.jobStatusRepository = jobStatusRepository;
        this.dataExportService = dataExportService;
        this.fileNameGenerator = fileNameGenerator;
    }

    @Async
    @Override
    public CompletableFuture<Long> exportDataAsync() {

        long startTime = System.currentTimeMillis();

        try {
            // Save the job status as "IN PROGRESS"
            JobStatus jobStatus = new JobStatus(JobType.EXPORT, JobStatusEnum.IN_PROGRESS);
            jobStatus = jobStatusRepository.save(jobStatus);
            Long jobId = jobStatus.getId();

            String desktopPath = System.getProperty("user.home") + "/Desktop";
            String filePath = desktopPath + fileNameGenerator.generateFileName();
            dataExportService.exportDataToExcel(filePath);

            // Update the job status as "DONE" and save the file path
            jobStatus.setStatus(JobStatusEnum.DONE);
            jobStatus.setResult("Data export completed successfully.");
            jobStatus.setFilePath(filePath); // Set the file path
            jobStatusRepository.save(jobStatus);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            logger.info("Data export completed in {} milliseconds.", duration);


            return CompletableFuture.completedFuture(jobId);
        } catch (Exception e) {
            // If there's an error, update the job status as "ERROR"
            JobStatus jobStatus = new JobStatus(JobType.EXPORT, JobStatusEnum.ERROR);
            jobStatus.setResult("Export failed: " + e.getMessage());
            jobStatusRepository.save(jobStatus);

            logger.error("Export failed: {}", e.getMessage(), e);


            return CompletableFuture.completedFuture(-1L);
        }
    }


    @Override
    public String getStatusByIdAndType(String id) {
        JobStatus jobStatus = jobStatusRepository.findByIdAndJobType(Long.parseLong(id), JobType.EXPORT).orElse(null);

        if (jobStatus == null) {
            return JobType.EXPORT + " job with ID " + id + " not found";
        }

        String status = jobStatus.getStatus().name();
        String result = jobStatus.getResult();

        return JobType.EXPORT + " job ID: " + id + ", Status: " + status + ", Result: " + result;
    }


    @Override
    public ResponseEntity<Resource> downloadExportedFile(Long jobId) throws IOException {
        // Retrieve the JobStatus entity by ID
        Optional<JobStatus> jobStatusOptional = jobStatusRepository.findByIdAndJobType(jobId, JobType.EXPORT);

        if (jobStatusOptional.isPresent()) {
            JobStatus jobStatus = jobStatusOptional.get();

            if (jobStatus.getStatus() == JobStatusEnum.DONE && jobStatus.getFilePath() != null) {
                String filePath = jobStatus.getFilePath();
                File file = new File(filePath);

                if (file.exists()) {
                    // Create an InputStreamResource from the file
                    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                    // Determine the media type of the file based on its extension
                    MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
                    if (filePath.toLowerCase().endsWith(".xlsx")) {
                        mediaType = MediaType.valueOf("application/vnd.ms-excel");
                    }

                    // Set the content disposition header for the response
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

                    // Return the file as a ResponseEntity with appropriate headers and media type
                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentType(mediaType)
                            .body(resource);
                }
            } else if (jobStatus.getStatus() == JobStatusEnum.IN_PROGRESS) {
                logger.warn("Exporting is in progress. File download is not allowed.");
                throw new IllegalStateException("Exporting is in progress. File download is not allowed.");
            }
        }

        // If the file is not found, the job status is not "DONE," or other errors occur, return an appropriate response
        logger.warn("File not found or job status is not 'DONE' for ID: {}", jobId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found or job status is not 'DONE' for ID: " + jobId);
    }

}

