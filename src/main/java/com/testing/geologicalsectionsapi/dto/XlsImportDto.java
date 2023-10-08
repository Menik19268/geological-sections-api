package com.testing.geologicalsectionsapi.dto;

import lombok.Data;

import java.util.Map;

//@Data
//public class XlsImportDto {
//
//    private String sectionName;
//    private String className;
//    private String classCode;
//}


@Data
public class XlsImportDto {
    private String sectionName;
    private Map<String, String> classData;
}

