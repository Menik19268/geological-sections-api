package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.JobStatusEnum;
import com.testing.geologicalsectionsapi.models.JobType;
import com.testing.geologicalsectionsapi.services.ExportService;
import com.testing.geologicalsectionsapi.services.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/import-export")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ImportExportController {

    private final ImportService importService;
    private final ExportService exportDataAsync;

    public ImportExportController(ImportService importService, ExportService exportDataAsync) {
        this.importService = importService;
        this.exportDataAsync = exportDataAsync;
    }

    @PostMapping("/{traceId}/{channel}/{user}/import")
    @ApiOperation(value = "Import Data from XLS File", tags = "import-export-api")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) {
        try {
            // Call the import service to start the asynchronous import job
            CompletableFuture<Long> jobIdFuture = importService.importDataAsync(file);

            // Implement logic to wait for the job to complete and get the job ID
            Long jobId = jobIdFuture.get(); // This will block until the job is completed

            if (jobId != -1L) {
                // Implement logic to return the import job ID
                return new ResponseEntity<>("Import job started with ID: " + jobId, HttpStatus.OK);
            } else {
                // Handle the case where import failed
                return new ResponseEntity<>("Error during import, please check logs.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during import: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{traceId}/{channel}/{user}/import/{id}")
    @ApiOperation(value = "Get Import Status by ID", tags = "import-export-api")
    public ResponseEntity<String> getImportStatus(@PathVariable String id) {
        // Implement logic to check the status of the import job
        String importStatus = importService.getStatusByIdAndType(id);

        if (importStatus.startsWith("Import job with ID")) {
            return new ResponseEntity<>(importStatus, HttpStatus.NOT_FOUND);
        }
        // Return the import status and result
        return new ResponseEntity<>(importStatus, HttpStatus.OK);
    }


    @PostMapping("/{traceId}/{channel}/{user}/export")
    @ApiOperation(value = "Launch Exporting", tags = "import-export-api")
    public ResponseEntity<String> launchExport() {
        try {
            // Call the export service to start the asynchronous export job
            CompletableFuture<Long> jobIdFuture = exportDataAsync.exportDataAsync();

            // Implement logic to wait for the job to complete and get the job ID
            Long jobId = jobIdFuture.get(); // This will block until the job is completed

            if (jobId != -1L) {
                // Implement logic to return the export job ID
                return new ResponseEntity<>("Export job started with ID: " + jobId, HttpStatus.OK);
            } else {
                // Handle the case where export failed
                return new ResponseEntity<>("Error during export, please check logs.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during export: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{traceId}/{channel}/{user}/export/{id}")
    @ApiOperation(value = "Get Export Status by ID", tags = "import-export-api")
    public ResponseEntity<String> getExportStatus(@PathVariable String id) {

        // Implement logic to check the status of the import job
        String exportStatus = exportDataAsync.getStatusByIdAndType(id);

        if (exportStatus.startsWith("Export job with ID")) {
            return new ResponseEntity<>(exportStatus, HttpStatus.NOT_FOUND);
        }
        // Return the import status and result
        return new ResponseEntity<>(exportStatus, HttpStatus.OK);
    }


    @GetMapping("/{traceId}/{channel}/{user}/export/{id}/file")
    @ApiOperation(value = "Download Exported File by Job ID", tags = "import-export-api")
    public ResponseEntity<Resource> downloadExportedFile(@PathVariable String id) {
        try {
            ResponseEntity<Resource> response = exportDataAsync.downloadExportedFile(Long.valueOf(id));

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                // Handle other error cases if needed
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (IOException e) {
            // Handle IO exceptions if they occur during file download
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}