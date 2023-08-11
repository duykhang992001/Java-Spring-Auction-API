package com.hcmus.auction.common.variable.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class UserIdResponse {
    @JsonProperty(value = "user_id")
    @ApiModelProperty(value = "User id", example = "5aded52b-e0a6-4d33-a6df-a493727c3107")
    private String userId;
}
