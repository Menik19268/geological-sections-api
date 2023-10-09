package com.testing.geologicalsectionsapi.models;

import lombok.Data;

@Data
public class GeologicalClassResponse {

    Long id;
    private String name;
    private String code;
    SectionResponse sectionResponse;


}
