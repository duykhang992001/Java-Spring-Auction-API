package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @ApiModelProperty(value = "User email", example = "ddk992001@gmail.com")
    private String email;

    @JsonProperty(value = "is_activated")
    @ApiModelProperty(value = "Whether account is activated", example = "true")
    private Boolean isActivated;

    private UserDTO user;
}
