package com.testing.geologicalsectionsapi.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
public class CommonErrorResponse {
    @ApiModelProperty(value = "This is the HTTP status", required = true)
    private int status;

    @ApiModelProperty(value = "This is the custom message ", required = true)
    private String message;

    @ApiModelProperty(value = "This is the timestamp")
    private long timeStamp;

}
