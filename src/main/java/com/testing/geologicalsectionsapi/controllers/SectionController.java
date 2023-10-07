package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.services.SectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SectionController.BASE_URL)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {

    static final String BASE_URL = "/api";

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @ApiOperation(value = "Create a new Section", tags = "geological-sections-api")
    @PostMapping(value = "/{traceId}/{channel}/{user}/section", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        Section createdSection = sectionService.createSection(section);
        return new ResponseEntity<>(createdSection, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a Section by ID", tags = "geological-sections-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/section/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Section> getSectionById(@PathVariable Long id, @PathVariable String traceId) {
        Section section = sectionService.getSectionById(id, traceId);
        if (section != null) {
            return new ResponseEntity<>(section, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Update a Section by ID", tags = "geological-sections-api")
    @PutMapping(value = "/{traceId}/{channel}/{user}/section/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section updatedSection, @PathVariable String traceId) {
        Section updatedSectionResult = sectionService.updateSection(id, updatedSection, traceId);
        if (updatedSectionResult != null) {
            return new ResponseEntity<>(updatedSectionResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a Section by ID", tags = "geological-sections-api")
    @DeleteMapping(value = "/{traceId}/{channel}/{user}/section/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        boolean deleted = sectionService.deleteSection(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
