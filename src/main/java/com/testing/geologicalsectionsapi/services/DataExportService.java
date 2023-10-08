package com.testing.geologicalsectionsapi.services;

import java.io.IOException;

public interface DataExportService {

    void exportDataToExcel(String filePath) throws IOException;
}
