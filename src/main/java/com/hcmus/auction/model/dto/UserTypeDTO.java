package com.hcmus.auction.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeDTO {
    @ApiModelProperty(value = "User type id", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    private String id;

    @ApiModelProperty(value = "User type name", example = "Bidder")
    private String name;
}
