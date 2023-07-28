package com.hcmus.auction.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "num_of_like")
    private Integer numOfLike;

    @Column(name = "num_of_dislike")
    private Integer numOfDislike;

    @Column(name = "end_seller_timestamp")
    private Integer endSellerTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id")
    private UserType type;

    @OneToMany(mappedBy = "currentWinner")
    private List<Product> winProducts = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Product> ownProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuctionHistory> auctionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FavoriteProduct> favoriteProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RoleHistory> roleHistories = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Review> reviewsBySelf = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Review> reviewsByOther = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuctionRequest> auctionRequests = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Account account;
}
