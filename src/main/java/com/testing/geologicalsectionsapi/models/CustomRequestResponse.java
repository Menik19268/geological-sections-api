package com.testing.geologicalsectionsapi.models;

/**
 * Created by menik on 19/08/2019.
 */


public class CustomRequestResponse extends CommonErrorResponse {


    public CustomRequestResponse(int status, String message, long timeStamp) {
        super(status, message, timeStamp);
    }

    public CustomRequestResponse() {
    }
}
