package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ApiModelProperty(value = "Outer category id", example = "b8b1a65b-8597-47b4-ae1b-ec4210c320f2")
    @JsonProperty(value = "outer_category_id")
    private String outerCategoryId;
}
