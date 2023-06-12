package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.InnerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InnerCategoryRepository extends JpaRepository<InnerCategory, String> {
}
