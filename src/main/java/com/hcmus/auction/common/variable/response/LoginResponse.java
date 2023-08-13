package com.hcmus.auction.common.variable.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hcmus.auction.model.dto.AccountDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty(value = "account")
    private AccountDTO account;

    @JsonProperty(value = "access_token")
    @ApiModelProperty(value = "Access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;

    @JsonProperty(value = "refresh_token")
    @ApiModelProperty(value = "Refresh token", example = "f3cd420d-7f24-423a-b283-3bfa0df1980d")
    private String refreshToken;
}
