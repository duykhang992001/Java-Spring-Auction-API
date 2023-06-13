package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.DescriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionHistoryRepository extends JpaRepository<DescriptionHistory, String> {
}
