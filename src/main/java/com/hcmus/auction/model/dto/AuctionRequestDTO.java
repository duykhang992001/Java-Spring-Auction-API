package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionRequestDTO {
    @ApiModelProperty(value = "Auction request id", example = "22153a72-8ac6-4b96-85b7-a0a898e25d78")
    private String id;

    @JsonProperty(value = "created_at")
    @ApiModelProperty(value = "Auction request created timestamp", example = "1686710955")
    private Integer createdAt;

    @JsonProperty(value = "is_accepted")
    @ApiModelProperty(value = "Whether auction request is accepted by owner", example = "true")
    private Boolean isAccepted;

    @JsonProperty(value = "product_id")
    @ApiModelProperty(value = "Auction request product id", example = "b1ea1d03-9d1d-4fbf-acfd-b56e099c2dae")
    private String productId;

    @ApiModelProperty(value = "Auction request bidder info")
    private UserDTO bidder;
}
