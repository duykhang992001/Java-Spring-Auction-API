package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class ProfileRequest {
    @ApiModelProperty(value = "New user name", example = "Dang Duy Khanggg")
    private String name;

    @ApiModelProperty(value = "New user address", example = "171 Dao Duy Tu Street, District 8, Ho Chi Minh City")
    private String address;

    @ApiModelProperty(value = "New user email", example = "ddk882001@gmail.com")
    private String email;
}
