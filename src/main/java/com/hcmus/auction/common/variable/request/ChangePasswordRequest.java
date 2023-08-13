package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class ChangePasswordRequest {
    @JsonProperty(value = "old_password")
    @ApiModelProperty(value = "Old password", example = "12345")
    private String oldPassword;

    @JsonProperty(value = "new_password")
    @ApiModelProperty(value = "New password", example = "123456")
    private String newPassword;
}
