package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.AuctionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRequestRepository extends JpaRepository<AuctionRequest, String> {
    Page<AuctionRequest> findAllByUserIdAndProductId(String userId, String productId, Pageable pageable);
}
