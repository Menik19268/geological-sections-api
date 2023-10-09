package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.GeologicalClass;

import com.testing.geologicalsectionsapi.services.impl.GeologicalClassServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(GeologicalClassController.BASE_URL)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GeologicalClassController {

    static final String BASE_URL = "/api";


    private final GeologicalClassServiceImpl geologicalClassService;
    final Logger logger = LoggerFactory.getLogger("Application");


    public GeologicalClassController(GeologicalClassServiceImpl geologicalClassService) {
        this.geologicalClassService = geologicalClassService;
    }

    @ApiOperation(value = "Create a new GeologicalClass", tags = "geological-classes-api")
    @PostMapping(value = "/{traceId}/{channel}/{user}/geological-class", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> createGeologicalClass(@RequestBody GeologicalClass geologicalClass, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to create Geological Class from channel {} ||  user {}  ", channel, user);
            GeologicalClass createdGeologicalClass = geologicalClassService.createGeologicalClass(geologicalClass);
            return new ResponseEntity<>(createdGeologicalClass, HttpStatus.CREATED);
        } finally {
            MDC.remove("transaction.id");
        }

    }

    @ApiOperation(value = "Get a GeologicalClass by ID", tags = "geological-classes-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> getGeologicalClassById(@PathVariable Long id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request a Geological Class  from channel {} ||  user {}  ", channel, user);
            Optional<GeologicalClass> geologicalClassOptional = geologicalClassService.getGeologicalClassById(id);

            if (geologicalClassOptional.isPresent()) {
                GeologicalClass geologicalClass = geologicalClassOptional.get();
                return new ResponseEntity<>(geologicalClass, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } finally {
            MDC.remove("transaction.id");
        }
    }


    @ApiOperation(value = "Update a GeologicalClass by ID", tags = "geological-classes-api")
    @PutMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClass> updateGeologicalClass(@PathVariable Long id, @RequestBody GeologicalClass updatedGeologicalClass, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to Update a Geological Class from channel {} ||  user {}  ", channel, user);
            GeologicalClass updatedClass = geologicalClassService.updateGeologicalClass(id, updatedGeologicalClass);
            if (updatedClass != null) {
                return new ResponseEntity<>(updatedClass, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } finally {
            MDC.remove("transaction.id");
        }
    }

    @ApiOperation(value = "Delete a GeologicalClass by ID", tags = "geological-classes-api")
    @DeleteMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGeologicalClass(@PathVariable Long id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to Update a Geological Class  from channel {} ||  user {}  ", channel, user);
            boolean deleted = geologicalClassService.deleteGeologicalClass(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } finally {
            MDC.remove("transaction.id");
        }
    }


}
