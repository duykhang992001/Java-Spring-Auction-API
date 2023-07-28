package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class AuctionRequest {
    @JsonProperty(value = "user_id")
    @ApiModelProperty(value = "User id needs to auction the product", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    private String userId;

    @ApiModelProperty(value = "Auction price", example = "1000000")
    private Integer price;
}
