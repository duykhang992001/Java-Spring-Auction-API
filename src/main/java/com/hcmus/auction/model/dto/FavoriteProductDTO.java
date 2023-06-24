package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProductDTO {
    private String id;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "created_at")
    private Integer createdAt;

    private ProductDTO product;
}
