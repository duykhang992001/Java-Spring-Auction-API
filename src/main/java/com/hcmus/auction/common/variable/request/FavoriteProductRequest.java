package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class FavoriteProductRequest {
    @JsonProperty(value = "product_id")
    @ApiModelProperty(value = "Product id", example = "0a6eaf66-1339-4471-b1af-64ba9c628ff6")
    private String productId;
}
