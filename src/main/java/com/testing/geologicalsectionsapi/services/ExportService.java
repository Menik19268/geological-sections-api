package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.models.JobStatusEnum;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ExportService {

    CompletableFuture<Long> exportDataAsync();

    String getStatusByIdAndType(String id);

    ResponseEntity<Resource> downloadExportedFile(Long jobId) throws IOException;
}
