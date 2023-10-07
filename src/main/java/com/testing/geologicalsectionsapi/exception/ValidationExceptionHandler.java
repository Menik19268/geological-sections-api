package com.testing.geologicalsectionsapi.exception;

import com.testing.geologicalsectionsapi.models.response.Data;
import com.testing.geologicalsectionsapi.models.response.Error;
import com.testing.geologicalsectionsapi.models.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @Value("${invalid.message}")
    String invalidMessage;

    @Value("${error.code.reference}")
    String referenceURL;


    @ExceptionHandler
    public ResponseEntity<Response> handleError(ValidationException e) {

        // create BadRequestResponse
        Response response = new Response();
        response.setMessage(this.invalidMessage);
        response.setCode("0000");
        response.setTraceId(e.getTraceId());
        response.setType("Validation");
        response.setDetails(this.referenceURL);
        response.setTimestamp(LocalDateTime.now().toString());

        List<Error> errorList = new ArrayList<>();

        Data data = new Data();

        Error error = new Error();
        error.setCode(e.code);
        error.setMessage(e.getMessage());
        error.setField(e.getField());

        errorList.add(error);

        data.setError(errorList);

        response.setData(data);


        //return ResponseEntity
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
