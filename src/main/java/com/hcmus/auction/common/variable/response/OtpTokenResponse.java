package com.hcmus.auction.common.variable.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class OtpTokenResponse {
    @JsonProperty(value = "token")
    @ApiModelProperty(value = "Token is used for final step when resetting password", example = "ac1ava7va-bav1a679a")
    private String token;
}
