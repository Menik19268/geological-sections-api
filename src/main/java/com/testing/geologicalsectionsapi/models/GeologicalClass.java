package com.testing.geologicalsectionsapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import javax.persistence.*;

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
