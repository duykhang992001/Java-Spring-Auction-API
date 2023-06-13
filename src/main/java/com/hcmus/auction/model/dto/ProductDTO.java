package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @ApiModelProperty(value = "Product id", example = "0a6eaf66-1339-4471-b1af-64ba9c628ff6")
    private String id;

    @ApiModelProperty(value = "Product name", example = "Adidas Champions League | Pro Soccer Ball 2023 Istanbul | KNOCK OUT Match Ball")
    private String name;

    @JsonProperty(value = "current_price")
    @ApiModelProperty(value = "Product current price", example = "1200000")
    private Integer currentPrice;

    @JsonProperty(value = "buy_now_price")
    @ApiModelProperty(value = "Product buy now price", example = "3000000")
    private Integer buyNowPrice;

    @JsonProperty(value = "start_timestamp")
    @ApiModelProperty(value = "Product created timestamp", example = "1686632400")
    private Integer startTimestamp;

    @JsonProperty(value = "end_timestamp")
    @ApiModelProperty(value = "Product ended timestamp", example = "1687237200")
    private Integer endTimestamp;

    @JsonProperty(value = "num_of_bid")
    @ApiModelProperty(value = "Number of product's bid", example = "8")
    private Integer numOfBid;

    @JsonProperty(value = "additional_price")
    @ApiModelProperty(value = "Product additional price", example = "200000")
    private Integer additionalPrice;

    @JsonProperty(value = "is_auto_extend_time")
    @ApiModelProperty(value = "Whether product is auto extend time or not when reaching a new auction before 5 minutes ending", example = "true")
    private Boolean isAutoExtendTime;

    @ApiModelProperty(value = "Product category")
    private InnerCategoryDTO category;

    @JsonProperty(value = "current_winner")
    @ApiModelProperty(value = "Current winner of product")
    private UserDTO currentWinner;

    @ApiModelProperty(value = "Product owner")
    private UserDTO owner;

    @ApiModelProperty(value = "List of product images")
    private List<ImageDTO> images = new ArrayList<>();

    @JsonProperty(value = "description_histories")
    @ApiModelProperty(value = "List of product descriptions")
    private List<DescriptionHistoryDTO> descriptionHistories = new ArrayList<>();
}
