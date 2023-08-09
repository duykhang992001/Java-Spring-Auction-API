package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findAllByRecipientId(String userId, Pageable pageable);
    boolean existsBySenderIdAndProductId(String senderId, String productId);
    List<Review> findAllByProductId(String productId);
}
