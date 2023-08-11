package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class SignUpRequest {
    @ApiModelProperty(value = "User email", example = "ddk992001@gmail.com")
    private String email;

    @ApiModelProperty(value = "User password", example = "12345")
    private String password;

    @ApiModelProperty(value = "User full name", example = "Dang Duy Khang")
    private String name;

    @ApiModelProperty(value = "User address", example = "227 Nguyen Van Cu, TPHCM")
    private String address;
}
