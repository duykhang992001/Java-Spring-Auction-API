package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    @ApiModelProperty(value = "Image id", example = "01ced0df-5eef-49fd-bfbc-483b9a7ceb2c")
    private String id;

    @ApiModelProperty(value = "Image url", example = "https://i.ebayimg.com/images/g/IuUAAOSwBxhkQiZa/s-l1600.jpg")
    private String url;

    @JsonProperty(value = "is_thumbnail_image")
    @ApiModelProperty(value = "Whether image is thumbnail or not", example = "true")
    private Boolean isThumbnailImage;
}
