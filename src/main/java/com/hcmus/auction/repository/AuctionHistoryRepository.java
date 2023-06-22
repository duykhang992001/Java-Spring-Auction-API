package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.AuctionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, String> {
    Page<AuctionHistory> findAllByProductId(String productId, Pageable pageable);
}
