package com.testing.geologicalsectionsapi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

    @JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private String code;
    @JsonProperty("traceId")
    private String traceId;
    @JsonProperty("timestamp")
    private String timestamp;


}
