package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.AuctionRequestDTO;
import org.springframework.data.domain.Page;

public interface AuctionRequestService {
    int getRequestStatus(String userId, String productId);
    void addNewAuctionRequest(String userId, String productId);
    Page<AuctionRequestDTO> getUnacceptedAuctionRequestsByProductId(String productId, Integer page, Integer size);
    void acceptAuctionRequest(String requestId);
    void declineAuctionRequest(String requestId);
}
