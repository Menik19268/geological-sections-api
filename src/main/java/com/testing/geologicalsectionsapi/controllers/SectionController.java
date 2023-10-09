package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.services.SectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SectionController.BASE_URL)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {

    static final String BASE_URL = "/api";

    private final SectionService sectionService;
    final Logger logger = LoggerFactory.getLogger("Application");


    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @ApiOperation(value = "Creating a Section", tags = "geological-sections-api")
    @PostMapping(path = "/{traceId}/{channel}/{user}/sections", produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public Section createSection(@RequestBody Section section, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to create section from channel {} ||  user {}  ", channel, user);
            return sectionService.createSection(section);
        } finally {
            MDC.remove("transaction.id");
        }
    }

    @ApiOperation(value = "Getting a Section", tags = "geological-sections-api")
    @GetMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public Section getSectionById(@PathVariable Long id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request a Section  from channel {} ||  user {}  ", channel, user);
            return sectionService.getSectionById(id, traceId);
        } finally {
            MDC.remove("transaction.id");
        }

    }

    @ApiOperation(value = "Update a Section", tags = "geological-sections-api")
    @PutMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public Section updateSection(@PathVariable Long id, @RequestBody Section section, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to Update a Section  from channel {} ||  user {}  ", channel, user);
            return sectionService.updateSection(id, section, traceId);
        } finally {
            MDC.remove("transaction.id");
        }

    }


    @ApiOperation(value = "Delete a Section", tags = "connection-disconnect-reconnect")
    @DeleteMapping(path = "/{traceId}/{channel}/{user}/sections/{id}", produces = "application/json")
    @ResponseBody
    public void deleteSection(@PathVariable Long id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {
        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to Update a Section  from channel {} ||  user {}  ", channel, user);
            sectionService.deleteSection(id);
        } finally {
            MDC.remove("transaction.id");
        }
    }


    @ApiOperation(value = "Get Sections by GeologicalClass Code", tags = "geological-sections-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/section/by-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Section>> getSectionsByGeologicalClassCode(@RequestParam(name = "code") String code, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to Get a Section by the class ID from channel {} ||  user {}  ", channel, user);
            List<Section> sections = sectionService.getSectionsByGeologicalClassCode(code);

            if (!sections.isEmpty()) {
                return new ResponseEntity<>(sections, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } finally {
            MDC.remove("transaction.id");
        }
    }

}
