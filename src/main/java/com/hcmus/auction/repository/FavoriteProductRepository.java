package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.FavoriteProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, String> {
    Optional<FavoriteProduct> findByUserIdAndProductId(String userId, String productId);
    Page<FavoriteProduct> findByUserIdAndProductEndTimestampLessThanEqual(String userId, Integer endTimestamp, Pageable pageable);
    Page<FavoriteProduct> findByUserIdAndProductEndTimestampGreaterThanEqual(String userId, Integer endTimestamp, Pageable pageable);
    void deleteByUserIdAndProductId(String userId, String productId);
}
