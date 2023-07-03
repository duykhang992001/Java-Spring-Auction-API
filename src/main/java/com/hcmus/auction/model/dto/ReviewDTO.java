package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    @ApiModelProperty(value = "Review id", example = "5edbf779-cdd5-4c12-af12-b61c7b297ee0")
    private String id;

    @ApiModelProperty(value = "Review status: like or dislike", example = "true")
    @JsonProperty(value = "is_liked")
    private Boolean isLiked;

    @ApiModelProperty(value = "Review comment", example = "Good")
    private String comment;

    @ApiModelProperty(value = "Review created timestamp", example = "1686710955")
    @JsonProperty(value = "created_at")
    private Integer createdAt;

    @ApiModelProperty(value = "Reviewed product")
    private ProductDTO product;

    @ApiModelProperty(value = "User sends review")
    private UserDTO sender;

    @JsonProperty(value = "recipient_id")
    @ApiModelProperty(value = "User id receives review", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    private String recipientId;
}
