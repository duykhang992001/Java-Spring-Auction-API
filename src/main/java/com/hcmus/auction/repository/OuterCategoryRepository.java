package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.OuterCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuterCategoryRepository extends JpaRepository<OuterCategory, String> {
}
