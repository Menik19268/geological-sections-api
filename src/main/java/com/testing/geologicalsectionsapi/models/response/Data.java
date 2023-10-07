
package com.testing.geologicalsectionsapi.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
public class Data {

    @JsonProperty("error")
    private List<Error> error = null;

}
