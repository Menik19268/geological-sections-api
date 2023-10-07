
package com.testing.geologicalsectionsapi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Error {

    @JsonProperty("code")
    private String code;
    @JsonProperty("field")
    private String field;
    @JsonProperty("message")
    private String message;

}
