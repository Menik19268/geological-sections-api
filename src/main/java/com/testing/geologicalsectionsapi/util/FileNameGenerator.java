package com.testing.geologicalsectionsapi.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileNameGenerator {

    public String generateFileName() {
        String commonName = "exported_data"; // Replace with your common name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileExtension = ".xlsx"; // Replace with your desired file extension

        String fileName = commonName + "_" + timeStamp + fileExtension;
        return fileName;
    }
}
