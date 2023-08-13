package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class EmailRequest {
    @ApiModelProperty(value = "User email", example = "github.ddk992001@gmail.com")
    private String email;
}
