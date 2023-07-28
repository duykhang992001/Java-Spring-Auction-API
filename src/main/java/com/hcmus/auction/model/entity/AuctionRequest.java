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
@Table(name = "auction_request")
public class AuctionRequest {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_at")
    private Integer createdAt;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
