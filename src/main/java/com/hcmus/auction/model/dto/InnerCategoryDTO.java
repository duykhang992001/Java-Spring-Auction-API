package com.hcmus.auction.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnerCategoryDTO {
    @ApiModelProperty(value = "Inner category id", example = "da72868b-2459-4e08-b043-46af854afcca")
    private String id;

    @ApiModelProperty(value = "Inner category name", example = "Laptops and Notebooks")
    private String name;
}
