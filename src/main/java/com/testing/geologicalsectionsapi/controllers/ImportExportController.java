package com.testing.geologicalsectionsapi.controllers;


import com.testing.geologicalsectionsapi.services.ExportService;
import com.testing.geologicalsectionsapi.services.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/import-export")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ImportExportController {

    private final ImportService importService;
    private final ExportService exportDataAsync;

    final Logger logger = LoggerFactory.getLogger("Application");


    public ImportExportController(ImportService importService, ExportService exportDataAsync) {
        this.importService = importService;
        this.exportDataAsync = exportDataAsync;
    }

    @PostMapping("/{traceId}/{channel}/{user}/import")
    @ApiOperation(value = "Import Data from XLS File", tags = "import-export-api")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {

            MDC.put("transaction.id", traceId);
            logger.info("Request to Import data file from channel {} | user {} ", channel, user);
            CompletableFuture<Long> jobIdFuture = importService.importDataAsync(file);

            Long jobId = jobIdFuture.get();

            if (jobId != -1L) {
                return new ResponseEntity<>("Import job started with ID: " + jobId, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error during import, please check logs.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during import: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            MDC.remove("transaction.id");
        }
    }

    @GetMapping("/{traceId}/{channel}/{user}/import/{id}")
    @ApiOperation(value = "Get Import Status by ID", tags = "import-export-api")
    public ResponseEntity<String> getImportStatus(@PathVariable String id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request Import status of the JOB id {} | user {} ", id, channel, user);
            String importStatus = importService.getStatusByIdAndType(id);

            if (importStatus.startsWith("Import job with ID")) {
                return new ResponseEntity<>(importStatus, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(importStatus, HttpStatus.OK);
        } finally {
            MDC.remove("transaction.id");
        }

    }


    @PostMapping("/{traceId}/{channel}/{user}/export")
    @ApiOperation(value = "Launch Exporting", tags = "import-export-api")
    public ResponseEntity<String> launchExport(@PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {

            MDC.put("transaction.id", traceId);
            logger.info("Request to export data file from channel {} | user {} ", channel, user);
            CompletableFuture<Long> jobIdFuture = exportDataAsync.exportDataAsync();

            Long jobId = jobIdFuture.get();
            if (jobId != -1L) {
                return new ResponseEntity<>("Export job started with ID: " + jobId, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error during export, please check logs.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during export: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            MDC.remove("transaction.id");
        }
    }


    @GetMapping("/{traceId}/{channel}/{user}/export/{id}")
    @ApiOperation(value = "Get Export Status by ID", tags = "import-export-api")
    public ResponseEntity<String> getExportStatus(@PathVariable String id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request Export status of the JOB id {} | user {} ", id, channel, user);
            String exportStatus = exportDataAsync.getStatusByIdAndType(id);

            if (exportStatus.startsWith("Export job with ID")) {
                return new ResponseEntity<>(exportStatus, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(exportStatus, HttpStatus.OK);
        } finally {
            MDC.remove("transaction.id");
        }
    }


    @GetMapping("/{traceId}/{channel}/{user}/export/{id}/file")
    @ApiOperation(value = "Download Exported File by Job ID", tags = "import-export-api")
    public ResponseEntity<Resource> downloadExportedFile(@PathVariable String id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request Export file of the JOB id {} | user {} ", id, channel, user);
            ResponseEntity<Resource> response = exportDataAsync.downloadExportedFile(Long.valueOf(id));

            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            MDC.remove("transaction.id");
        }
    }

}