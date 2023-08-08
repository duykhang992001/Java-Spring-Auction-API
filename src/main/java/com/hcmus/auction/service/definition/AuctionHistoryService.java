package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;

import com.hcmus.auction.model.entity.AuctionHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuctionHistoryService {
    Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
    Page<String> getAuctioningProductIdByUserId(String userId, Integer page, Integer size);
    void addNewAuctionHistory(String userId, String productId, Integer price);
    List<String> declineAuctionHistory(String auctionId);
    boolean isAbleToAuction(String userId, String productId);
}
