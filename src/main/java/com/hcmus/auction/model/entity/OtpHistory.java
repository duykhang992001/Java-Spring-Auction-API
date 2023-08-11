package com.hcmus.auction.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Data
@Table(name = "otp_history")
public class OtpHistory {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "start_timestamp")
    private Integer startTimestamp;

    @Column(name = "end_timestamp")
    private Integer endTimestamp;

    @Column(name = "value")
    private String value;

    @Column(name = "is_used_for_sign_up")
    private Boolean isUsedForSignUp;

    @Column(name = "is_used")
    private Boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
