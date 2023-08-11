package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.OtpHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpHistoryRepository extends JpaRepository<OtpHistory, String> {
    Page<OtpHistory> findAllByUserIdAndIsUsedForSignUp(String userId, Boolean isUsedForSignUp, Pageable pageable);
}
