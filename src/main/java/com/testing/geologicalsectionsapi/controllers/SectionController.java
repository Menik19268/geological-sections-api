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

    @ApiOperation(value = "/{traceId}/{channel}/{user}/sections", tags = "geological-sections-api")
    @PostMapping(path = "/{traceId}/{channel}/{user}/sections", produces = "application/json")
    @ResponseBody
    public Section createSection(@RequestBody Section section) {
        return sectionService.createSection(section);
    }

    @ApiOperation(value = "/{traceId}/{channel}/{user}/sections/{id}", tags = "geological-sections-api")
    @GetMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public Section getSectionById(@PathVariable Long id, @PathVariable String traceId) {

        return sectionService.getSectionById(id, traceId);

    }

    @ApiOperation(value = "/process-discon-recon/{traceId}/{channel}/{user}/customer", tags = "geological-sections-api")
    @PutMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public Section updateSection(@PathVariable Long id, @RequestBody Section section, @PathVariable String traceId) {
        return sectionService.updateSection(id, section, traceId);
    }


    @ApiOperation(value = "/{traceId}/{channel}/{user}/sections/{id}", tags = "connection-disconnect-reconnect")
    @DeleteMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public void deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }



    @ApiOperation(value = "Get Sections by GeologicalClass Code", tags = "geological-sections-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/section/by-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Section>> getSectionsByGeologicalClassCode(@RequestParam(name = "code") String code) {
        List<Section> sections = sectionService.getSectionsByGeologicalClassCode(code);

        if (!sections.isEmpty()) {
            return new ResponseEntity<>(sections, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
