package com.hcmus.auction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpHistoryDTO {
    private String id;

    @JsonProperty(value = "user_id")
    private String userId;

    private String value;

    @JsonProperty(value = "start_timestamp")
    private Integer startTimestamp;

    @JsonProperty(value = "end_timestamp")
    private Integer endTimestamp;

    @JsonProperty(value = "is_used_for_sign_up")
    private Boolean isUsedForSignUp;

    @JsonProperty(value = "is_used")
    private Boolean isUsed;
}
