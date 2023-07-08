package com.hcmus.auction.common.variable.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonSerialize
@Data
public class ImageRequest {
    @ApiModelProperty(value = "Image url", example = "https://i.ebayimg.com/images/g/IuUAAOSwBxhkQiZa/s-l1600.jpg")
    private String url;

    @JsonProperty(value = "is_thumbnail_image")
    @ApiModelProperty(value = "Whether image is thumbnail or not", example = "true")
    private Boolean isThumbnailImage;
}
