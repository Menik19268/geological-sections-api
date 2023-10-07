
package com.testing.geologicalsectionsapi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;


@lombok.Data
public class Response extends SuccessResponse {


    @JsonProperty("type")
    private String type;
    @JsonProperty("details")
    private String details;
    @JsonProperty("data")
    private Data data;

    public Response(String message, String code, String traceId, String timestamp, String type, String details, Data data) {
        super(message, code, traceId, timestamp);
        this.type = type;
        this.details = details;
        this.data = data;
    }

    public Response(String message, String code, String traceId, String timestamp) {
        super(message, code, traceId, timestamp);
    }

    public Response() {

    }





}
