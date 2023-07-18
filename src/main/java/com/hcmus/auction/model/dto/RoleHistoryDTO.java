package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleHistoryDTO {
    @ApiModelProperty(value = "Role history id", example = "90e5507b-8c7b-4a84-a0dc-4167f14c3a6b")
    private String id;

    @ApiModelProperty(value = "User id upgrade or downgrade", example = "989dc613-6390-4299-8631-c46e603fe3b7")
    @JsonProperty(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "Role history created timestamp", example = "1687854763")
    @JsonProperty(value = "created_at")
    private Integer createdAt;

    @ApiModelProperty(value = "Role history updated timestamp", example = "1687854769")
    @JsonProperty(value = "updated_at")
    private Integer updatedAt;

    @ApiModelProperty(value = "Whether role history is accepted", example = "true")
    @JsonProperty(value = "is_accepted")
    private Boolean isAccepted;

    @ApiModelProperty(value = "Whether role history is upgraded", example = "true")
    @JsonProperty(value = "is_upgraded")
    private Boolean isUpgraded;
}
