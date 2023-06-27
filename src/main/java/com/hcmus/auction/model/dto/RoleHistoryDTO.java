package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleHistoryDTO {
    private String id;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "created_at")
    private Integer createdAt;

    @JsonProperty(value = "updated_at")
    private Integer updatedAt;

    @JsonProperty(value = "is_accepted")
    private Boolean isAccepted;

    @JsonProperty(value = "is_upgraded")
    private Boolean isUpgraded;
}
