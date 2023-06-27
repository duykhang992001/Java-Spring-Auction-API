package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.RoleHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleHistoryRepository extends JpaRepository<RoleHistory, String> {
    Page<RoleHistory> findByUserId(String userId, Pageable pageable);
}
