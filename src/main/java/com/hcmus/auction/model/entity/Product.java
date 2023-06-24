package com.hcmus.auction.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "current_price")
    private Integer currentPrice;

    @Column(name = "buy_now_price")
    private Integer buyNowPrice;

    @Column(name = "start_timestamp")
    private Integer startTimestamp;

    @Column(name = "end_timestamp")
    private Integer endTimestamp;

    @Column(name = "num_of_bid")
    private Integer numOfBid;

    @Column(name = "additional_price")
    private Integer additionalPrice;

    @Column(name = "is_auto_extend_time")
    private Boolean isAutoExtendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inner_category_id")
    private InnerCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_winner_id")
    private User currentWinner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<DescriptionHistory> descriptionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<AuctionHistory> auctionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<FavoriteProduct> favoriteProducts = new ArrayList<>();
}
