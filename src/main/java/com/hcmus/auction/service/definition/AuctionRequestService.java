package com.hcmus.auction.service.definition;

public interface AuctionRequestService {
    int getRequestStatus(String userId, String productId);
    void addNewAuctionRequest(String userId, String productId);
}
