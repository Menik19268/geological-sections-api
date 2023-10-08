package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.dto.XlsImportDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImportService {

    CompletableFuture<Long> importDataAsync(MultipartFile file);

    String getStatusByIdAndType(String id);
}
