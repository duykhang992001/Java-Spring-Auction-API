package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionHistoryDTO {
    @ApiModelProperty(value = "Auction history id", example = "22153a72-8ac6-4b96-85b7-a0a898e25d78")
    private String id;

    @JsonProperty(value = "product_id")
    @ApiModelProperty(value = "Auction history product id", example = "b1ea1d03-9d1d-4fbf-acfd-b56e099c2dae")
    private String productId;

    @JsonProperty(value = "created_at")
    @ApiModelProperty(value = "Auction history created timestamp", example = "1686710955")
    private Integer createdAt;

    @ApiModelProperty(value = "Auction history price", example = "3800000")
    private Integer price;

    @JsonProperty(value = "is_rejected")
    @ApiModelProperty(value = "Whether auction history is rejected by owner", example = "false")
    private Boolean isRejected;

    @ApiModelProperty(value = "Auction history bidder info")
    private UserDTO bidder;
}
