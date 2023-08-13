package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class LoginRequest {
    @JsonProperty(value = "email")
    @ApiModelProperty(value = "User email", example = "github.ddk992001@gmail.com")
    private String email;

    @JsonProperty(value = "password")
    @ApiModelProperty(value = "User password", example = "12345")
    private String password;
}
