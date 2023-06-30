package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProductDTO {
    @ApiModelProperty(value = "Favorite product id", example = "364589f8-1366-4574-a8a0-ae7f29109fd1")
    private String id;

    @JsonProperty(value = "user_id")
    @ApiModelProperty(value = "User id likes product", example = "cb3e0bca-9e15-4ac6-9aa0-e5368fb1f1e4")
    private String userId;

    @JsonProperty(value = "created_at")
    @ApiModelProperty(value = "Favorite product created timestamp", example = "1687769704")
    private Integer createdAt;

    @ApiModelProperty(value = "Product info")
    private ProductDTO product;
}
