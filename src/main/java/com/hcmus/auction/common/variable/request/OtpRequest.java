package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class OtpRequest {
    @ApiModelProperty(value = "User id needs to be verified", example = "5aded52b-e0a6-4d33-a6df-a493727c3107")
    @JsonProperty(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "Otp code", example = "123456")
    @JsonProperty(value = "otp_code")
    private String otpCode;
}
