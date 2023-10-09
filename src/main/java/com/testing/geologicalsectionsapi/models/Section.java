package com.testing.geologicalsectionsapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
import jakarta.persistence.*;
>>>>>>> feature/api-logging-config
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<GeologicalClass> geologicalClasses;

}
