package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.services.impl.GeologicalClassServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/geological-classes")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GeologicalClassController {

    private final GeologicalClassServiceImpl geologicalClassService;


    public GeologicalClassController(GeologicalClassServiceImpl geologicalClassService) {
        this.geologicalClassService = geologicalClassService;
    }

    @ApiOperation(value = "Create a new GeologicalClass", tags = "geological-classes-api")
    @PostMapping(value = "/{traceId}/{channel}/{user}/geological-class", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> createGeologicalClass(@RequestBody GeologicalClass geologicalClass) {
        GeologicalClass createdGeologicalClass = geologicalClassService.createGeologicalClass(geologicalClass);
        return new ResponseEntity<>(createdGeologicalClass, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a GeologicalClass by ID", tags = "geological-classes-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> getGeologicalClassById(@PathVariable Long id) {
        Optional<GeologicalClass> geologicalClassOptional = geologicalClassService.getGeologicalClassById(id);

        if (geologicalClassOptional.isPresent()) {
            GeologicalClass geologicalClass = geologicalClassOptional.get();
            return new ResponseEntity<>(geologicalClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation(value = "Update a GeologicalClass by ID", tags = "geological-classes-api")
    @PutMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> updateGeologicalClass(@PathVariable Long id, @RequestBody GeologicalClass updatedGeologicalClass) {
        GeologicalClass updatedClass = geologicalClassService.updateGeologicalClass(id, updatedGeologicalClass);
        if (updatedClass != null) {
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a GeologicalClass by ID", tags = "geological-classes-api")
    @DeleteMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGeologicalClass(@PathVariable Long id) {
        boolean deleted = geologicalClassService.deleteGeologicalClass(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
