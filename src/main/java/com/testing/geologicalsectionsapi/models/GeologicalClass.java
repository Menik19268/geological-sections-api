package com.testing.geologicalsectionsapi.models;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class GeologicalClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    // Default constructor
    public GeologicalClass() {
    }

    // Constructor with parameters
    public GeologicalClass(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
