package com.testing.geologicalsectionsapi.exception;

import com.testing.geologicalsectionsapi.models.CustomRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<CustomRequestResponse> handleError(CustomRequestException e) {

        // create BadRequestResponse
        CustomRequestResponse customRequestResponse = new CustomRequestResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getLocalizedMessage(),
                System.currentTimeMillis());


        //return ResponseEntity
        return new ResponseEntity<>(customRequestResponse, HttpStatus.BAD_REQUEST);
    }
}
