package com.testing.geologicalsectionsapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImportService {

    CompletableFuture<Long> importDataAsync(MultipartFile file);

    String getStatusByIdAndType(String id);
}
