package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.AuctionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, String> {
    Page<AuctionHistory> findAllByProductIdAndIsRejected(String productId, Boolean isRejected, Pageable pageable);

    @Query(value = "SELECT DISTINCT ah.product_id FROM auction_history ah JOIN product p ON ah.product_id = p.id " +
            "WHERE ah.user_id = ?1 AND ah.is_rejected = 0 AND p.end_timestamp >= ?2", nativeQuery = true)
    Page<String> getAuctioningProductIdByUserId(String userId, Integer endTimestamp, Pageable pageable);
}
