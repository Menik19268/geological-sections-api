package com.testing.geologicalsectionsapi.services;

import com.testing.geologicalsectionsapi.dto.XlsImportDto;

import java.util.List;

public interface DataImportService {

    void mapAndSaveData(List<XlsImportDto> importedData);
}
