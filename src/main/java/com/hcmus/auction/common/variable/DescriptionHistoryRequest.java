package com.hcmus.auction.common.variable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class DescriptionHistoryRequest {
    @ApiModelProperty(value = "Description content", example = "<p>Hello</p>")
    private String content;
}
