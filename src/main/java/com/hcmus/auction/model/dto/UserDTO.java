package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ApiModelProperty(value = "User id", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    private String id;

    @ApiModelProperty(value = "User name", example = "Dang Duy Khang")
    private String name;

    @ApiModelProperty(value = "User address", example = "2A/7 Street 10, Ward 13, District 6")
    private String address;

    @JsonProperty(value = "num_of_like")
    @ApiModelProperty(value = "Number of user's like", example = "8")
    private Integer numOfLike;

    @JsonProperty(value = "num_of_dislike")
    @ApiModelProperty(value = "Number of user's dislike", example = "1")
    private Integer numOfDislike;
}
