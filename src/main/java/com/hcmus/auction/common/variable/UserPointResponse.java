package com.hcmus.auction.common.variable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@JsonSerialize
@AllArgsConstructor
public class UserPointResponse {
    @JsonProperty(value = "num_of_like")
    @ApiModelProperty(value = "Number of user's like", example = "5")
    private Integer numOfLike;

    @JsonProperty(value = "num_of_dislike")
    @ApiModelProperty(value = "Number of user's dislike", example = "0")
    private Integer numOfDislike;
}
