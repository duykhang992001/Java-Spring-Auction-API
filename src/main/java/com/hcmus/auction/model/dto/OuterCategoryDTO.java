package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OuterCategoryDTO {
    @ApiModelProperty(value = "Outer category id", example = "b8b1a65b-8597-47b4-ae1b-ec4210c320f2")
    private String id;

    @ApiModelProperty(value = "Outer category name", example = "Electronics")
    private String name;

    @JsonProperty(value = "inner_categories")
    @ApiModelProperty(value = "List of inner categories")
    private List<InnerCategoryDTO> innerCategoryDTOs = new ArrayList<>();
}
