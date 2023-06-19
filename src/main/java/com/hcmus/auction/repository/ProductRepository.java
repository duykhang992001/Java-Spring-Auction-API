package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByEndTimestampGreaterThanEqual(Integer endTimestamp, Pageable pageable);
    Optional<Product> findByIdAndEndTimestampGreaterThanEqual(String id, Integer endTimestamp);
    Page<Product> findAllByCategoryIdAndEndTimestampGreaterThanEqual(String categoryId, Integer endTimestamp, Pageable pageable);
    Page<Product> findAllByCategoryIdAndEndTimestampGreaterThanEqualAndIdNot(
            String categoryId, Integer endTimestamp, String exclusiveProductId, Pageable pageable);
}
