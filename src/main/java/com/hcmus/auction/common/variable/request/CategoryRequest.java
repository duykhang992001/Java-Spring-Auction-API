package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class CategoryRequest {
    @ApiModelProperty(value = "Category name", example = "Food")
    private String name;
}
