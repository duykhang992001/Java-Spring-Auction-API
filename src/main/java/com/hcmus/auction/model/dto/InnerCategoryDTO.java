package com.hcmus.auction.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnerCategoryDTO {
    @ApiModelProperty(value = "Inner category id", example = "55bff322-b180-4ec1-bfa1-e87ca92ab541")
    private String id;

    @ApiModelProperty(value = "Inner category name", example = "Laptops and Notebooks")
    private String name;
}
