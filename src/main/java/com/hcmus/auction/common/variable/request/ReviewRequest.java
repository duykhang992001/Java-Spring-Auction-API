package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class ReviewRequest {
    @JsonProperty(value = "sender_id")
    @ApiModelProperty(value = "Review sender id", example = "d96416a7-a78b-40e5-a26f-c146c3177ebb")
    private String senderId;

    @ApiModelProperty(value = "Review comment", example = "Good product")
    private String comment;

    @JsonProperty(value = "is_liked")
    @ApiModelProperty(value = "Review status", example = "true")
    private Boolean isLiked;
}
