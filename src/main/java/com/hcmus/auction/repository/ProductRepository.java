package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByEndTimestampGreaterThanEqual(Integer endTimestamp, Pageable pageable);
    Page<Product> findAllByEndTimestampLessThanEqual(Integer endTimestamp, Pageable pageable);
    Page<Product> findAllByCategoryIdAndEndTimestampGreaterThanEqual(String categoryId, Integer endTimestamp, Pageable pageable);
    Page<Product> findAllByCategoryIdAndEndTimestampGreaterThanEqualAndIdNot(
            String categoryId, Integer endTimestamp, String exclusiveProductId, Pageable pageable);
    Page<Product> findAllByCategoryIdAndEndTimestampLessThanEqual(String categoryId, Integer endTimestamp, Pageable pageable);
    Page<Product> findAllByCategoryIdAndEndTimestampLessThanEqualAndIdNot(
            String categoryId, Integer endTimestamp, String exclusiveProductId, Pageable pageable);
    Page<Product> findAllByCurrentWinnerIdAndEndTimestampLessThanEqual(String userId, Integer endTimestamp, Pageable pageable);

    @Query(value = "SELECT p.* FROM product p JOIN inner_category ic ON p.inner_category_id = ic.id " +
            "WHERE p.end_timestamp >= ?2 AND (MATCH(ic.name) AGAINST (?1) OR MATCH(p.name) AGAINST (?1))", nativeQuery = true)
    Page<Product> searchWithGteEndTimestamp(String keyword, Integer endTimestamp, Pageable pageable);

    @Query(value = "SELECT p.* FROM product p JOIN inner_category ic ON p.inner_category_id = ic.id " +
            "WHERE p.end_timestamp <= ?2 AND (MATCH(ic.name) AGAINST (?1) OR MATCH(p.name) AGAINST (?1))", nativeQuery = true)
    Page<Product> searchWithLteEndTimestamp(String keyword, Integer endTimestamp, Pageable pageable);
}
