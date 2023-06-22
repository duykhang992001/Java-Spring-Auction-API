package com.hcmus.auction.controller.definition;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProductController {
    ResponseEntity<Page<AuctionHistoryDTO>> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
}
