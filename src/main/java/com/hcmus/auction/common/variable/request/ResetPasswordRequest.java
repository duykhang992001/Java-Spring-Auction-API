package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class ResetPasswordRequest {
    @JsonProperty(value = "token")
    @ApiModelProperty(value = "Verified token", example = "ac1ava7va-bav1a679a")
    private String token;

    @JsonProperty(value = "user_id")
    @ApiModelProperty(value = "User id", example = "5aded52b-e0a6-4d33-a6df-a493727c3107")
    private String userId;

    @JsonProperty(value = "password")
    @ApiModelProperty(value = "New password", example = "123456")
    private String password;
}
