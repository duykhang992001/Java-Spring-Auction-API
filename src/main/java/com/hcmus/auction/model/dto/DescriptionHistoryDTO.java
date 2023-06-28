package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionHistoryDTO {
    @ApiModelProperty(value = "Description id", example = "09319271-1752-46f6-887e-29714fb28c1a")
    private String id;

    @JsonProperty(value = "product_id")
    @ApiModelProperty(value = "Product id of description", example = "0a6eaf66-1339-4471-b1af-64ba9c628ff6")
    private String productId;

    @JsonProperty(value = "created_at")
    @ApiModelProperty(value = "Description created timestamp", example = "1686546000")
    private Integer createdAt;

    @ApiModelProperty(value = "Description content", example = "<p><span><strong>All Colours & Sizes Available</strong></span></p>\n" +
            "<p><span><strong>Size:</strong> S-2XL</span></p>\n" +
            "<p><span><strong>To Fit - S</strong> 34”-36” <strong>M</strong> 38” <strong>L</strong> 40”-42” <strong>XL</strong> 44”-46” <strong>2XL</strong> 48”-50”</span></p>")
    private String content;
}
