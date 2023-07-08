package com.hcmus.auction.common.variable.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status_code")
    private Integer statusCode;
}
