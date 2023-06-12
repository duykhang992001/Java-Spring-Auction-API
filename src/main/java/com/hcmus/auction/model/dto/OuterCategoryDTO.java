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
    @ApiModelProperty(value = "Outer category id", example = "34d00d61-41bb-4436-9c24-0a3a3bcc6e33")
    private String id;

    @ApiModelProperty(value = "Outer category name", example = "Electronics")
    private String name;

    @JsonProperty(value = "inner_categories")
    @ApiModelProperty(value = "List of inner categories")
    private List<InnerCategoryDTO> innerCategoryDTOs = new ArrayList<>();
}
