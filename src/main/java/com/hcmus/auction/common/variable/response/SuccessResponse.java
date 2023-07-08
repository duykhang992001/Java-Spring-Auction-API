package com.hcmus.auction.common.variable.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class SuccessResponse {
    @JsonProperty(value = "message")
    @ApiModelProperty(value = "Return message", example = "Message Here!")
    private String message;
}
