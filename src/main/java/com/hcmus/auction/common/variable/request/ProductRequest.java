package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize
@Data
public class ProductRequest {
    @ApiModelProperty(value = "Product name", example = "Adidas Champions League | Pro Soccer Ball 2023 Istanbul | KNOCK OUT Match Ball")
    private String name;

    @JsonProperty(value = "current_price")
    @ApiModelProperty(value = "Product current price", example = "1200000")
    private Integer currentPrice;

    @JsonProperty(value = "additional_price")
    @ApiModelProperty(value = "Product additional price", example = "200000")
    private Integer additionalPrice;

    @JsonProperty(value = "buy_now_price")
    @ApiModelProperty(value = "Product buy now price", example = "3000000")
    private Integer buyNowPrice;

    @JsonProperty(value = "is_auto_extend_time")
    @ApiModelProperty(value = "Whether product is auto extend time or not when reaching a new auction before 5 minutes ending", example = "true")
    private Boolean isAutoExtendTime;

    @JsonProperty(value = "start_timestamp")
    @ApiModelProperty(value = "Product created timestamp", example = "1686632400")
    private Integer startTimestamp;

    @JsonProperty(value = "end_timestamp")
    @ApiModelProperty(value = "Product ended timestamp", example = "1687237200")
    private Integer endTimestamp;

    @JsonProperty(value = "category_id")
    @ApiModelProperty(value = "Category id", example = "da72868b-2459-4e08-b043-46af854afcca")
    private String categoryId;

    @JsonProperty(value = "owner_id")
    @ApiModelProperty(value = "Owner id", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    private String ownerId;

    @ApiModelProperty(value = "Product description", example = "<p><span><strong>All Colours & Sizes Available</strong></span></p>\n" +
            "<p><span><strong>Size:</strong> S-2XL</span></p>\n" +
            "<p><span><strong>To Fit - S</strong> 34”-36” <strong>M</strong> 38” <strong>L</strong> 40”-42” <strong>XL</strong> 44”-46” <strong>2XL</strong> 48”-50”</span></p>")
    private String description;

    private List<ImageRequest> images = new ArrayList<>();
}
