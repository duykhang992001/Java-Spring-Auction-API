package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import org.springframework.data.domain.Page;

public interface AuctionHistoryService {
    Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
}
