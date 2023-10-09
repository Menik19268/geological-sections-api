package com.testing.geologicalsectionsapi.controllers;

import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.services.impl.GeologicalClassServiceImpl;
import com.testing.geologicalsectionsapi.util.JwtTokenProvider;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/geological-classes")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GeologicalClassController {

    private final GeologicalClassServiceImpl geologicalClassService;
<<<<<<< HEAD
    private final JwtTokenProvider jwtTokenProvider;
=======
    final Logger logger = LoggerFactory.getLogger("Application");
>>>>>>> feature/api-logging-config

    public GeologicalClassController(GeologicalClassServiceImpl geologicalClassService, JwtTokenProvider jwtTokenProvider) {
        this.geologicalClassService = geologicalClassService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "Create a new GeologicalClass", tags = "geological-classes-api")
    @PostMapping(value = "/{traceId}/{channel}/{user}/geological-class", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
    public ResponseEntity<GeologicalClass> createGeologicalClass(@RequestBody GeologicalClass geologicalClass, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        if (jwtTokenProvider.validateToken(jwtToken)) {

//            String username = jwtTokenProvider.getUsernameFromToken(jwtToken);

            GeologicalClass createdGeologicalClass = geologicalClassService.createGeologicalClass(geologicalClass);
            return new ResponseEntity<>(createdGeologicalClass, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


=======
    public ResponseEntity<GeologicalClass> createGeologicalClass(@RequestBody GeologicalClass geologicalClass, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request to create Geological Class from channel {} ||  user {}  ", channel, user);
            GeologicalClass createdGeologicalClass = geologicalClassService.createGeologicalClass(geologicalClass);
            return new ResponseEntity<>(createdGeologicalClass, HttpStatus.CREATED);
        } finally {
            MDC.remove("transaction.id");
        }

>>>>>>> feature/api-logging-config
    }

    @ApiOperation(value = "Get a GeologicalClass by ID", tags = "geological-classes-api")
    @GetMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeologicalClass> getGeologicalClassById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {

        String jwtToken = authorizationHeader.replace("Bearer ", "");

        if (jwtTokenProvider.validateToken(jwtToken)) {

=======
    public ResponseEntity<GeologicalClass> getGeologicalClassById(@PathVariable Long id, @PathVariable String traceId, @PathVariable String channel, @PathVariable String user) {

        try {
            MDC.put("transaction.id", traceId);
            logger.info("Request a Geological Class  from channel {} ||  user {}  ", channel, user);
>>>>>>> feature/api-logging-config
            Optional<GeologicalClass> geologicalClassOptional = geologicalClassService.getGeologicalClassById(id);

            if (geologicalClassOptional.isPresent()) {
                GeologicalClass geologicalClass = geologicalClassOptional.get();
                return new ResponseEntity<>(geologicalClass, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
<<<<<<< HEAD
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
=======

        } finally {
            MDC.remove("transaction.id");
>>>>>>> feature/api-logging-config
        }
    }


    @ApiOperation(value = "Update a GeologicalClass by ID", tags = "geological-classes-api")
    @PutMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeologicalClass> updateGeologicalClass(@PathVariable Long id, @RequestBody GeologicalClass updatedGeologicalClass, @RequestHeader("Authorization") String authorizationHeader) {
        GeologicalClass updatedClass = geologicalClassService.updateGeologicalClass(id, updatedGeologicalClass);
        if (updatedClass != null) {
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
=======
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
>>>>>>> feature/api-logging-config
        }
    }

    @ApiOperation(value = "Delete a GeologicalClass by ID", tags = "geological-classes-api")
    @DeleteMapping(value = "/{traceId}/{channel}/{user}/geological-class/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGeologicalClass(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        boolean deleted = geologicalClassService.deleteGeologicalClass(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
=======
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
>>>>>>> feature/api-logging-config
        }
    }


}
